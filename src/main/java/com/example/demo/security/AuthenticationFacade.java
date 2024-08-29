package com.example.demo.security;

import com.example.demo.models.Account;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    String getEmail();

    Boolean evaluateByEmail(Account account);
}
