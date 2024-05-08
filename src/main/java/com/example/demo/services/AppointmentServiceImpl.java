package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.enums.AppointmentStatus;
import com.example.demo.models.request.CreateAppointmentRequest;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.models.response.AvailableAppointmentIntervalResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AccountRepository accountRepository;

    private static final ZoneId DEFAULT_TIMEZONE_ID = ZoneId.systemDefault();

    @Override
    public List<AvailableAppointmentIntervalResponse> getDoctorAppointmentIntervals(UUID doctorId) {
        final Instant now = Instant.now();
        final Instant firstIntervalStartTime = LocalDate.now().atStartOfDay(DEFAULT_TIMEZONE_ID).toInstant();
        final Instant lastIntervalStartTime = firstIntervalStartTime.plus(14, ChronoUnit.DAYS);

        final List<Appointment> existingAppointmentList = this.appointmentRepository.findAllByDoctorIdInInterval(doctorId, firstIntervalStartTime, lastIntervalStartTime);
        final List<AvailableAppointmentIntervalResponse> appointmentIntervals = new ArrayList<>();

        ZonedDateTime currentStartTime = LocalDate.now().atStartOfDay(DEFAULT_TIMEZONE_ID);
        while ( currentStartTime.toInstant().isBefore(lastIntervalStartTime)) {

            if (currentStartTime.toInstant().isBefore(now)) {
                currentStartTime = currentStartTime.plus(15, ChronoUnit.MINUTES);
                continue;
            }
            if (currentStartTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentStartTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                currentStartTime = currentStartTime.plus(15, ChronoUnit.MINUTES);
                continue;
            }
            if (currentStartTime.getHour() < 9 || currentStartTime.getHour() >= 17) {
                currentStartTime = currentStartTime.plus(15, ChronoUnit.MINUTES);
                continue;
            }
            Instant finalCurrentStartTime = currentStartTime.toInstant();
            final boolean isAvailable = existingAppointmentList.stream().noneMatch(ea -> ea.getAppointmentDate().equals(finalCurrentStartTime));
            appointmentIntervals.add(new AvailableAppointmentIntervalResponse(
                    currentStartTime.toInstant(),
                    currentStartTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    currentStartTime.getDayOfWeek(),
                    currentStartTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.uu")),
                    isAvailable));
            currentStartTime = currentStartTime.plus(15, ChronoUnit.MINUTES);
        }

        return appointmentIntervals;
    }

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        if (request.startTime().atZone(DEFAULT_TIMEZONE_ID).get(ChronoField.MINUTE_OF_HOUR) % 15 != 0) {
            throw new RuntimeException("Invalid start time!");
        }
        if (request.startTime().atZone(DEFAULT_TIMEZONE_ID).get(ChronoField.SECOND_OF_MINUTE) != 0) {
            throw new RuntimeException("Invalid start time!");
        }
        final Doctor doctor = this.doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new RuntimeException("Invalid doctor id!"));
        final Account account = this.accountRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Invalid patient id!"));
        final Appointment appointment = new Appointment();
        appointment.setUuid(UUID.randomUUID());
        appointment.setAppointmentDate(request.startTime());
        appointment.setDoctor(doctor);
        appointment.setPatient(account);
        appointment.setStatus(AppointmentStatus.ACTIVE);

        Appointment savedAppointment = this.appointmentRepository.save(appointment);
        return new AppointmentResponse(
                savedAppointment.getUuid(),
                savedAppointment.getDoctor().getUuid(),
                savedAppointment.getPatient().getUuid(),
                savedAppointment.getAppointmentDate().atZone(DEFAULT_TIMEZONE_ID).toLocalDate(),
                savedAppointment.getAppointmentDate(),
                savedAppointment.getAppointmentDate().plus(15, ChronoUnit.MINUTES));
    }

    @Override
    public Appointment cancelAppointment(UUID appointmentId) {
        return null;
    }
}
