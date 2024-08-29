package com.example.demo.services;

import com.example.demo.models.enums.Role;
import com.example.demo.models.request.RegistrationKeycloakRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class KeycloakServiceImpl implements KeycloakService{
    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;

    public KeycloakServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public void createUser(RegistrationKeycloakRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        //TODO change to false after email verification development
        user.setEmailVerified(true);
        user.setRealmRoles(new ArrayList<String>(Collections.singleton(Role.BASIC_USER.name())));

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(request.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);

        if (!Objects.equals(201, response.getStatus())){
            throw new RuntimeException("Error creating keycloak user");
        }
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }
}
