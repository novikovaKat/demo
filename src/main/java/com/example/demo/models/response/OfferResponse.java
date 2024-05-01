package com.example.demo.models.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OfferResponse(
        UUID serviceId,
        String name,
        BigDecimal price
){}