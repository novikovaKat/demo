package com.example.demo.services;



import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    private RestTemplate restTemplate;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;
    private static final String ACCESS_TOKEN_PATH = "/realms/demo/protocol/openid-connect/token";
    private static final String REFRESH_TOKEN_PATH = "/realms/demo/protocol/openid-connect/token";
    private static final String LOGOUT_PATH = "/realms/demo/protocol/openid-connect/logout";

    public AuthenticationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AccessTokenResponse getToken(String username, String password) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", username);
        requestBody.add("password", password);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, null);

        ResponseEntity<AccessTokenResponse> responseEntity = this.restTemplate.postForEntity(ACCESS_TOKEN_PATH, requestEntity, AccessTokenResponse.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }
        else{
            throw new RuntimeException("Error obtaining access token from Keycloak");
        }
    }

    @Override
    public AccessTokenResponse refreshToken(String refreshToken) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, null);

        ResponseEntity<AccessTokenResponse> responseEntity = this.restTemplate.postForEntity(REFRESH_TOKEN_PATH, requestEntity, AccessTokenResponse.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }
        else{
            throw new RuntimeException("Error refresh access token from Keycloak");
        }
    }

    @Override
    public void logout(String refreshToken) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("refresh_token", refreshToken);


        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, null);

        ResponseEntity<AccessTokenResponse> responseEntity = this.restTemplate.postForEntity(LOGOUT_PATH, requestEntity, AccessTokenResponse.class);

        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Error during logout user from Keycloak");
        }
    }
}
