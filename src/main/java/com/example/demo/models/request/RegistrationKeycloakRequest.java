package com.example.demo.models.request;

public record RegistrationKeycloakRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        String password
) {
}
