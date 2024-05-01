package com.example.demo.services;

import com.example.demo.models.response.OfferResponse;
import com.example.demo.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;
    @Override
    public List<OfferResponse> getOffersInfo() {
        return this.offerRepository.findAll().stream()
                .map(offer -> new OfferResponse(offer.getUuid(), offer.getName(), offer.getPrice()))
                .collect(Collectors.toList());
    }
}
