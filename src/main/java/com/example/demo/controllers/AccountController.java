package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public AccountResponse add(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.saveAccount(createAccountRequest);
    }
}
