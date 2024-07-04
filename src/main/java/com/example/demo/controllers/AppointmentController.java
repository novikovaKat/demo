package com.example.demo.controllers;

import com.example.demo.models.request.CreateAppointmentRequest;
import com.example.demo.models.response.AppointmentResponse;
import com.example.demo.models.response.AvailableAppointmentIntervalResponse;
import com.example.demo.services.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
@CrossOrigin
public class AppointmentController {
    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/all")
    public List<AppointmentResponse> getAllAppointments(){
        return this.appointmentService.getAllAppointments();
    }

    @PostMapping("/create")
    public AppointmentResponse createAppointment(@RequestBody final CreateAppointmentRequest request){
        return this.appointmentService.createAppointment(request);
    }

    @PutMapping("/cancel")
    public AppointmentResponse cancelAppointment(@RequestParam final UUID appointmentId){
        return this.appointmentService.cancelAppointment(appointmentId);
    }
    @GetMapping("/available/{doctorId}")
    public List<AvailableAppointmentIntervalResponse> getDoctorAppointmentIntervals(@PathVariable final UUID doctorId){
        return this.appointmentService.getDoctorAppointmentIntervals(doctorId);
    }

    @GetMapping("/{patientId}")
    public List<AppointmentResponse> getPatientAppointments(@PathVariable UUID patientId){
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @GetMapping("/{doctorId}")
    public List<AppointmentResponse> getDoctorAppointments(@PathVariable UUID doctorId){
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }
}
