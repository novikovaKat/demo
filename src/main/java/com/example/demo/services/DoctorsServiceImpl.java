package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.enums.AppointmentStatus;
import com.example.demo.models.response.DoctorAdministrativeResponse;
import com.example.demo.models.response.DoctorResponse;
import com.example.demo.models.response.DoctorViewResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorsServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorViewResponse> getAllDoctorsInfo() {
        return this.doctorRepository.findAll().stream()
                .map(doctor -> new DoctorViewResponse(doctor.getUuid(), doctor.getAccount().getFirstName(), doctor.getAccount().getLastName(), doctor.getSpecialties()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorViewResponse> getAllActiveDoctorsInfo() {
        return this.doctorRepository.findByStatusIsTrue().stream()
                .map(doctor -> new DoctorViewResponse(doctor.getUuid(), doctor.getAccount().getFirstName(), doctor.getAccount().getLastName(), doctor.getSpecialties()))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponse getDoctorByUuid(UUID uuid) {
        Doctor doctor = this.doctorRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("no such record!"));
        return new DoctorResponse(
                doctor.getUuid(),
                doctor.getAccount().getFirstName(),
                doctor.getAccount().getLastName(),
                doctor.getEducation(),
                doctor.getExperience(),
                doctor.getSpecialties());
    }

    @Override
    public DoctorAdministrativeResponse getDetailedDoctorByUuid(UUID uuid) {
        Doctor doctor = this.doctorRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("no such record!"));
        return new DoctorAdministrativeResponse(
                doctor.getUuid(),
                doctor.getAccount().getFirstName(),
                doctor.getAccount().getLastName(),
                doctor.getAccount().getEmail(),
                doctor.getAccount().getPhone(),
                doctor.getEducation(),
                doctor.getExperience(),
                doctor.getSpecialties());
    }

    @Override
    public DoctorResponse getDoctorByAccountId(UUID uuid) {
        Doctor doctor = this.doctorRepository.findByAccountUuid(uuid)
                .orElseThrow(() -> new RuntimeException("no such record!"));
        return new DoctorResponse(
                doctor.getUuid(),
                doctor.getAccount().getFirstName(),
                doctor.getAccount().getLastName(),
                doctor.getEducation(),
                doctor.getExperience(),
                doctor.getSpecialties());
    }

    @Override
    public DoctorResponse createDoctor(UUID accountId) {
        final Account account = this.accountRepository.findByUuid(accountId)
                .orElseThrow(() -> new RuntimeException("No such account"));
        Doctor doctor = new Doctor();
        doctor.setUuid(UUID.randomUUID());
        doctor.setAccount(account);
        doctor.setStatus(true);

        Doctor savedDoctor = this.doctorRepository.save(doctor);

        return new DoctorResponse(
                savedDoctor.getUuid(),
                savedDoctor.getAccount().getFirstName(),
                savedDoctor.getAccount().getLastName(),
                null,
                null,
                Set.of()
        );
    }

    @Override
    public void disableDoctor(UUID uuid) {
        Doctor doctor = this.doctorRepository.findByAccountUuid(uuid).orElseThrow(() -> new RuntimeException("No such doctor"));
        doctor.setStatus(false);

        List<Appointment> allByDoctorAccountUuid = this.appointmentRepository.findAllByDoctorAccountUuid(uuid);
        allByDoctorAccountUuid.forEach(appointment -> appointment.setStatus(AppointmentStatus.CANCELLED));
        this.appointmentRepository.saveAll(allByDoctorAccountUuid);
        this.doctorRepository.save(doctor);
    }
}
