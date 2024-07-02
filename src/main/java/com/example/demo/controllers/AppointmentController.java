package com.example.demo.controllers;

import com.example.demo.models.request.CreateAppointmentRequest;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.models.response.AvailableAppointmentIntervalResponse;
import com.example.demo.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
@CrossOrigin
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public AppointmentResponse createAppointment(@RequestBody final CreateAppointmentRequest request){
        return this.appointmentService.createAppointment(request);
    }

    @GetMapping("/free/by-doctor")
    public List<AvailableAppointmentIntervalResponse> getDoctorAppointmentIntervals(@RequestParam final UUID doctorId){
        return this.appointmentService.getDoctorAppointmentIntervals(doctorId);
    }

    @GetMapping("/all")
    public List<AppointmentResponse> getAllAppointments(){
        return this.appointmentService.getAllAppointments();
    }

    @PutMapping("/cancel")
    public AppointmentResponse cancelAppointment(@RequestParam final UUID appointmentId){
        return this.appointmentService.cancelAppointment(appointmentId);
    }
}
