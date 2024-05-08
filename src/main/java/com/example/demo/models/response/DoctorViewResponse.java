package com.example.demo.models.response;

import java.util.UUID;

public record DoctorViewResponse(
        UUID doctorId,
        String firstName,
        String lastName
) {
}
