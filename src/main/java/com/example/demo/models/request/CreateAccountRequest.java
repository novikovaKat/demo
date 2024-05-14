package com.example.demo.models.request;

public record CreateAccountRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        String password
) {
}
