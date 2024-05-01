package com.example.demo.services;

import com.example.demo.models.response.DoctorResponse;
import com.example.demo.models.response.DoctorViewResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    List<DoctorViewResponse> getAllDoctorsInfo();

    DoctorResponse getDoctorInfo(UUID uuid);
}
