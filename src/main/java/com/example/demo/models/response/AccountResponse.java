package com.example.demo.models.response;

import javax.swing.*;
import java.util.UUID;

public record AccountResponse(
        UUID uuid,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
