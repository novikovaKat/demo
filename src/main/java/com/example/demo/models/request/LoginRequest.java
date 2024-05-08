package com.example.demo.models.request;

public record LoginRequest(
        String email,
        String password
) {
}
