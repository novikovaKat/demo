package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;
    @Override
    @Transactional
    public AccountResponse saveAccount(CreateAccountRequest request) {
        Account account = new Account();
        account.setUuid(UUID.randomUUID());
        account.setFirstName(request.firstName());
        account.setLastName(request.lastName());
        account.setEmail(request.email());
        account.setPhone(request.phone());

        Account savedAccount = accountRepository.save(account);
        return new AccountResponse(
                savedAccount.getUuid(),
                savedAccount.getFirstName(),
                savedAccount.getLastName(),
                savedAccount.getEmail(),
                savedAccount.getPhone());
    }
}
