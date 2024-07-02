package com.example.demo.models.request;

public record UpdateAccountRequest(
    String firstName,
    String lastName,
    String email,
    String phone,
    String role) {
}
