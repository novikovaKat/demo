package com.example.demo.controllers;

import com.example.demo.models.request.CreateAccountRequest;
import com.example.demo.models.request.LoginRequest;
import com.example.demo.models.request.UpdateAccountRequest;
import com.example.demo.models.response.AccountResponse;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.models.response.DoctorResponse;
import com.example.demo.services.AccountService;
import com.example.demo.services.AppointmentService;
import com.example.demo.services.DoctorService;
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

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/signup")
    public AccountResponse add(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.saveAccount(createAccountRequest);
    }

    @PostMapping("/login")
    public AccountResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @GetMapping("/data")
    public AccountResponse getAccountData(@RequestParam UUID uuid){
        return accountService.getAccountData(uuid);
    }
    @GetMapping("/appointments")
    public List<AppointmentResponse> getPatientAppointments(@RequestParam UUID uuid){
        return appointmentService.getAppointmentsByPatientId(uuid);
    }

    @GetMapping("/doctor/data")
    public DoctorResponse getDoctorData(@RequestParam UUID uuid){
        return doctorService.getDoctorByAccountId(uuid);
    }

    @GetMapping("/doctor/appointments")
    public List<AppointmentResponse> getDoctorAppointments(@RequestParam UUID uuid){
        return appointmentService.getAppointmentsByDoctorId(uuid);
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
