package com.example.demo.services;

import com.example.demo.models.response.OfferResponse;

import java.util.List;

public interface OfferService {

    List<OfferResponse> getOffersInfo();
}