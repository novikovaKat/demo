package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.enums.Role;
import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    @Transactional
    public AccountResponse saveAccount(CreateAccountRequest request) {

        // add check for email exists in a DB
        if(accountRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already taken");
        }

        Account account = new Account();
        account.setUuid(UUID.randomUUID());
        account.setFirstName(request.firstName());
        account.setLastName(request.lastName());
        account.setEmail(request.email());
        account.setPhone(request.phone());
        account.setPassword(passwordEncoder.encode(request.password()));
        account.setRole(Role.BASIC_USER);

        Account savedAccount = accountRepository.save(account);
        return new AccountResponse(
                savedAccount.getUuid(),
                savedAccount.getFirstName(),
                savedAccount.getLastName(),
                savedAccount.getEmail(),
                savedAccount.getPhone(),
                savedAccount.getRole());
    }

    @Override
    public AccountResponse login(LoginRequest loginRequest) {
        Optional<Account> account = accountRepository.findByEmail(loginRequest.email());
        if(account.isEmpty()){
            throw new RuntimeException("Account doesn`t exist");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AccountResponse(
                account.get().getUuid(),
                account.get().getFirstName(),
                account.get().getLastName(),
                account.get().getEmail(),
                account.get().getPhone(),
                account.get().getRole());
    }
}
