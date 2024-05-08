package com.example.demo.models.request;

import java.time.Instant;
import java.util.UUID;

public record CreateAppointmentRequest(
        UUID doctorId,
        UUID patientId,
        Instant startTime
) {
}
