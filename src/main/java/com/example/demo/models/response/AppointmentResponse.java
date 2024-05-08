package com.example.demo.models.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record AppointmentResponse(
        UUID appointmentId,
        UUID doctorId,
        UUID patientId,
        LocalDate date,
        Instant startUtc,
        Instant endUtc
) {
}
