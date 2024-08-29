package com.example.demo.services;

import com.nimbusds.oauth2.sdk.TokenResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

public interface AuthenticationService {
    public AccessTokenResponse getToken(String username, String password);
    public AccessTokenResponse refreshToken(String refreshToken);
    public void logout(String refreshToken);

}
