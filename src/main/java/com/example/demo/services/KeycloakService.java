package com.example.demo.services;

import com.example.demo.models.request.RegistrationKeycloakRequest;

public interface KeycloakService {
    //TODO change the method type
    void createUser(RegistrationKeycloakRequest request);
}
