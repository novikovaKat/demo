package com.example.demo.controllers;

import com.example.demo.models.response.DoctorResponse;
import com.example.demo.models.response.DoctorViewResponse;
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

    @GetMapping
    public List<DoctorViewResponse> getAllDoctorsInfo(){
        return this.doctorService.getAllDoctorsInfo();
    }

    @GetMapping("/{doctorId}")
    public DoctorResponse getDoctorInfo(@PathVariable final UUID doctorId){
        return this.doctorService.getDoctorInfo(doctorId);
    }

}
