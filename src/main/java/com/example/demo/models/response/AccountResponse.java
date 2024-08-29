package com.example.demo.models.response;

import com.example.demo.models.enums.Role;

import java.util.UUID;

public record AccountResponse(
        UUID uuid,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
