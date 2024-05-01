package com.example.demo.models.response;

import java.util.UUID;

public record DoctorResponse(
        UUID doctorId,
        String firstName,
        String lastName,
        String specialty
) {
}
