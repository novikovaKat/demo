package com.example.demo.models.response;

import com.example.demo.models.Specialty;

import java.util.Set;
import java.util.UUID;

public record DoctorViewResponse(
        UUID doctorId,
        String firstName,
        String lastName,
        Set<Specialty> specialties
) {
}
