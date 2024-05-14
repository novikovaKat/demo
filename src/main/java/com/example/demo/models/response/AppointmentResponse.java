package com.example.demo.models.response;

import com.example.demo.models.enums.AppointmentStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentResponse(
        UUID appointmentId,
        UUID doctorId,
        String doctorFirstName,
        String doctorLastName,
        UUID patientId,
        String patientFirstName,
        String patientLastName,
        String date,
        Instant startUtc,
        Instant endUtc,
        String startTime,
        AppointmentStatus status
) {
}
