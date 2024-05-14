package com.example.demo.services;

import com.example.demo.models.Appointment;
import com.example.demo.models.request.CreateAppointmentRequest;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.models.response.AvailableAppointmentIntervalResponse;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    List<AvailableAppointmentIntervalResponse> getDoctorAppointmentIntervals(final UUID doctorId);

    AppointmentResponse createAppointment(final CreateAppointmentRequest request);

    Appointment cancelAppointment(final UUID appointmentId);

    List<AppointmentResponse> getAppointmentsByPatientId(final UUID patientId);
}
