package com.example.demo.controllers;

import com.example.demo.models.request.AccessTokenRequest;
import com.example.demo.services.AuthenticationService;
import com.nimbusds.oauth2.sdk.TokenResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public AccessTokenResponse getToken(@RequestBody AccessTokenRequest tokenRequest){
        return this.authenticationService.getToken(tokenRequest.username(), tokenRequest.password());
    }

    @PostMapping("/refresh")
    public AccessTokenResponse refreshToken(@RequestHeader("X-refresh-token") String refreshToken){
        return this.authenticationService.refreshToken(refreshToken);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("X-refresh-token") String refreshToken){
        this.authenticationService.logout(refreshToken);
    }

}
