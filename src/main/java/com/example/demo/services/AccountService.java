package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.response.AccountResponse;

public interface AccountService {
    public AccountResponse saveAccount(CreateAccountRequest createAccountRequest);
}
