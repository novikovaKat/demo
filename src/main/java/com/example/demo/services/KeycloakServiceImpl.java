package com.example.demo.services;

import com.example.demo.models.enums.Role;
import com.example.demo.models.request.RegistrationKeycloakRequest;
import com.example.demo.models.request.UpdateKeycloakRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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

        UsersResource usersResource = this.getUsersResource();

        Response response = usersResource.create(user);

        if (!Objects.equals(201, response.getStatus())){
            throw new RuntimeException("Error creating keycloak user");
        }
    }

    @Override
    public void updateUser(String email, UpdateKeycloakRequest request) {
        UsersResource usersResource = this.getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByEmail(email, true);

        if(!userRepresentations.isEmpty()){
            UserRepresentation user = userRepresentations.get(0);
            Optional<String> firstName = Optional.ofNullable(request.firstName());
            Optional<String> lastName = Optional.ofNullable(request.lastName());

            firstName.ifPresent(user::setFirstName);
            lastName.ifPresent(user::setLastName);

            UserResource userResource = usersResource.get(user.getId());
            userResource.update(user);
        }
        else{
            throw new RuntimeException("User is not found");
        }
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }
}
