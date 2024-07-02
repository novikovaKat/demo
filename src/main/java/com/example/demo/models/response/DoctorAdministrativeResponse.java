package com.example.demo.models.response;

import com.example.demo.models.Specialty;

import java.util.Set;
import java.util.UUID;

public record DoctorAdministrativeResponse (
        UUID doctorId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String education,
        String experience,
        Set<Specialty> specialties
){
}
