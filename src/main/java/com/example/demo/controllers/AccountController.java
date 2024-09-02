package com.example.demo.controllers;

import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.request.UpdateAccountRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@CrossOrigin("http://localhost:3000/")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public AccountResponse add(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.saveAccount(createAccountRequest);
    }

//    @PostMapping("/login")
//    public AccountResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
//        return accountService.login(loginRequest);
//    }

    @GetMapping("/{uuid}")
    public AccountResponse getAccountData(@PathVariable final UUID uuid){
        return accountService.getAccountData(uuid);
    }

    @GetMapping("/all")
    public List<AccountResponse> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/{uuid}")
    public AccountResponse updateAccount(@PathVariable final UUID uuid, @RequestBody UpdateAccountRequest updateAccountRequest){
        return accountService.updateAccount(uuid, updateAccountRequest);
    }


}
