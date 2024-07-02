package com.example.demo.controllers;

import com.example.demo.models.request.CreateAppointmentRequest;
import com.example.demo.models.response.*;
import com.example.demo.services.AppointmentService;
import com.example.demo.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<DoctorViewResponse> getAllActiveDoctorsInfo(){
        return this.doctorService.getAllActiveDoctorsInfo();
    }


    @GetMapping("/{doctorId}")
    public DoctorResponse getDoctorByUuid(@PathVariable final UUID doctorId){
        return this.doctorService.getDoctorByUuid(doctorId);
    }

    @GetMapping("/{doctorId}/detailed")
    public DoctorAdministrativeResponse getDetailedDoctorByUuid(@PathVariable final UUID doctorId){
        return this.doctorService.getDetailedDoctorByUuid(doctorId);
    }
    @GetMapping("/{doctorId}/appointments")
    public List<AvailableAppointmentIntervalResponse> getDoctorAppointmentIntervals(@PathVariable final UUID doctorId){
        return this.appointmentService.getDoctorAppointmentIntervals(doctorId);
    }

    @PostMapping("/appointment")
    public AppointmentResponse createAppointment(@RequestBody final CreateAppointmentRequest request){
        return this.appointmentService.createAppointment(request);
    }
}
