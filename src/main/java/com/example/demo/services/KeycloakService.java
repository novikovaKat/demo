package com.example.demo.services;

import com.example.demo.models.request.RegistrationKeycloakRequest;
import com.example.demo.models.request.UpdateKeycloakRequest;

public interface KeycloakService {
    //TODO change the method type
    void createUser(RegistrationKeycloakRequest request);

    void updateUser(String email, UpdateKeycloakRequest request);

}
