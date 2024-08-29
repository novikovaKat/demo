package com.example.demo.models.request;

public record AccessTokenRequest(
        String username,
        String password
) {
}
