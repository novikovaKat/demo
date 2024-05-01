package com.example.demo.controllers;

import com.example.demo.models.response.OfferResponse;
import com.example.demo.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
@CrossOrigin
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public List<OfferResponse> getOffersInfo(){
        return this.offerService.getOffersInfo();
    }
}
