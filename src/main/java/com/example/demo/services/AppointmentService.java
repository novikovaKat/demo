package com.example.demo.services;

import com.example.demo.models.Appointment;
import com.example.demo.models.request.CreateAppointmentRequest;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.models.response.AvailableAppointmentIntervalResponse;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    List<AvailableAppointmentIntervalResponse> getDoctorAppointmentIntervals(final UUID doctorId);

    AppointmentResponse createAppointment(final CreateAppointmentRequest request);

    AppointmentResponse cancelAppointment(final UUID appointmentId);

    @Scheduled(cron = "*/1 * * * *")
    void markExpiredAppointments();

    List<AppointmentResponse> getAppointmentsByPatientId(final UUID patientId);
    List<AppointmentResponse> getAppointmentsByDoctorId(final UUID doctorId);

    List<AppointmentResponse> getAllAppointments();
}
