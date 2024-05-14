package com.example.demo.services;

import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.response.AccountResponse;

public interface AccountService {
    public AccountResponse saveAccount(CreateAccountRequest createAccountRequest);

    AccountResponse login(LoginRequest loginRequest);
}
