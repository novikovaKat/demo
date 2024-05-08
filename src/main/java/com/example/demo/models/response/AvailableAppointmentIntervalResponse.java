package com.example.demo.models.response;

import java.time.DayOfWeek;
import java.time.Instant;

public record AvailableAppointmentIntervalResponse(
        Instant startUtc,
        String startTime,
        DayOfWeek dayOfWeek,
        String date,
        Boolean isAvailable
) {
}
