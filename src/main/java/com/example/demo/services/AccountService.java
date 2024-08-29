package com.example.demo.services;

import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.request.UpdateAccountRequest;
import com.example.demo.models.response.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponse saveAccount(CreateAccountRequest createAccountRequest);

//    AccountResponse login(LoginRequest loginRequest);

    AccountResponse getAccountData(UUID uuid);

    List<AccountResponse> getAllAccounts();

//    AccountResponse updateAccount(UUID uuid, UpdateAccountRequest updateAccountRequest);
}
