package com.example.demo.controllers;

import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/signup")
    public AccountResponse add(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.saveAccount(createAccountRequest);
    }

    @PostMapping("/login")
    public AccountResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }
}
