package com.example.demo.models.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAccountRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phone) {
}
