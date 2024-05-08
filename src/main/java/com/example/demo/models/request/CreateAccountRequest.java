package com.example.demo.models.request;

import com.example.demo.models.enums.Role;

public record CreateAccountRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        String password
) {
}
