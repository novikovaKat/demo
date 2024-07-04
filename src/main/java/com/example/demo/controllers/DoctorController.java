package com.example.demo.controllers;

import com.example.demo.models.response.DoctorAdministrativeResponse;
import com.example.demo.models.response.DoctorResponse;
import com.example.demo.models.response.DoctorViewResponse;
import com.example.demo.services.AppointmentService;
import com.example.demo.services.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctor")
@CrossOrigin
public class DoctorController {

    private DoctorService doctorService;
    private AppointmentService appointmentService;

    public DoctorController(DoctorService doctorService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public List<DoctorViewResponse> getAllActiveDoctorsInfo(){
        return this.doctorService.getAllActiveDoctorsInfo();
    }


    @GetMapping("/{doctorId}")
    public DoctorResponse getDoctorByUuid(@PathVariable final UUID doctorId){
        return this.doctorService.getDoctorByUuid(doctorId);
    }

    @GetMapping("/{accountId}")
    public DoctorResponse getDoctorByAccountId(@PathVariable final UUID accountId){
        return doctorService.getDoctorByAccountId(accountId);
    }

    @GetMapping("/{doctorId}/details")
    public DoctorAdministrativeResponse getDetailedDoctorByUuid(@PathVariable final UUID doctorId){
        return this.doctorService.getDetailedDoctorByUuid(doctorId);
    }
}
