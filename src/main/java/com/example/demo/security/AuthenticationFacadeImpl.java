package com.example.demo.security;

import com.example.demo.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade{

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getEmail() {
        return this.getAuthentication().getName();
    }

    @Override
    public Boolean evaluateByEmail(Account account) {
        return this.getEmail().equals(account.getEmail());
    }


}
