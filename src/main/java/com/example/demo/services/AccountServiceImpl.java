package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.RegistrationKeycloakRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.security.AuthenticationFacade;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    private DoctorRepository doctorRepository;
    private DoctorService doctorService;
    private AuthenticationFacade authenticationFacade;
    private KeycloakService keycloakService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              DoctorRepository doctorRepository,
                              DoctorService doctorService,
                              AuthenticationFacade authenticationFacade,
                              KeycloakService keycloakService) {
        this.accountRepository = accountRepository;
        this.doctorRepository = doctorRepository;
        this.doctorService = doctorService;
        this.authenticationFacade = authenticationFacade;
        this.keycloakService = keycloakService;
    }

    @Override
    public AccountResponse getAccountData(UUID uuid) {
        Optional<Account> account = accountRepository.findByUuid(uuid);
        if(account.isEmpty()){
            throw new RuntimeException("Account doesn`t exist");
        }

        if(this.authenticationFacade.evaluateByEmail(account.get())){
            return new AccountResponse(
                    account.get().getUuid(),
                    account.get().getFirstName(),
                    account.get().getLastName(),
                    account.get().getEmail(),
                    account.get().getPhone()
            );
        }
        else{
            throw new RuntimeException("Access denied");
        }
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return this.accountRepository.findAll().stream()
                .map(account -> new AccountResponse(
                        account.getUuid(),
                        account.getFirstName(),
                        account.getLastName(),
                        account.getEmail(),
                        account.getPhone()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountResponse saveAccount(CreateAccountRequest request) {
        this.keycloakService.createUser(
                new RegistrationKeycloakRequest(
                        request.email(),
                        request.email(),
                        request.firstName(),
                        request.lastName(),
                        request.password()));

        return new AccountResponse(UUID.randomUUID(),
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phone());

        //TODO work in progress
        /*// add check for email exists in a DB
        if(accountRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already taken");
        }

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
                savedAccount.getPhone());*/
    }

  /*  @Override
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
                account.get().getPhone());
    }*/

   /* @Override
    public AccountResponse updateAccount(UUID uuid, UpdateAccountRequest updateAccountRequest){
        Account account = this.accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("No such account"));
        Optional.ofNullable(updateAccountRequest.firstName()).ifPresent(account::setFirstName);
        Optional.ofNullable(updateAccountRequest.lastName()).ifPresent(account::setLastName);
        Optional.ofNullable(updateAccountRequest.email()).ifPresent(account::setEmail);
        Optional.ofNullable(updateAccountRequest.phone()).ifPresent(account::setPhone);
        Optional.ofNullable(updateAccountRequest.role()).ifPresent(f -> this.changeRole(Role.valueOf(f), account));

        Account updatedAccount = this.accountRepository.save(account);



        return new AccountResponse(
                updatedAccount.getUuid(),
                updatedAccount.getFirstName(),
                updatedAccount.getLastName(),
                updatedAccount.getEmail(),
                updatedAccount.getPhone(),
                updatedAccount.getRole());
    }

    private void changeRole(Role role, Account account){
        switch (role){
            case BASIC_USER, ADMIN -> {
                if(account.getRole() == Role.DOCTOR) {
                    this.doctorService.disableDoctor(account.getUuid());
                }
            }
            case DOCTOR -> {
                Optional<Doctor> doctor = this.doctorRepository.findByAccountUuid(account.getUuid());
                if(doctor.isPresent()){
                    doctor.get().setStatus(true);
                    this.doctorRepository.save(doctor.get());
                }
                else {
                    this.doctorService.createDoctor(account.getUuid());
                }
            }
        }
        account.setRole(role);
    }*/
}
