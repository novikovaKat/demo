package com.example.demo.controllers;

import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.services.AccountService;
import com.example.demo.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@CrossOrigin("http://localhost:3000/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/signup")
    public AccountResponse add(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.saveAccount(createAccountRequest);
    }

    @PostMapping("/login")
    public AccountResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @GetMapping("/appointments")
    public List<AppointmentResponse> getPatientAppointments(@RequestParam UUID uuid){
        return appointmentService.getAppointmentsByPatientId(uuid);
    }


}
