package com.example.demo.models.response;

import com.example.demo.models.Specialty;

import java.util.Set;
import java.util.UUID;

public record DoctorResponse(
        UUID doctorId,
        String firstName,
        String lastName,
        String education,
        String experience,
        Set<Specialty> specialties
) {
}
