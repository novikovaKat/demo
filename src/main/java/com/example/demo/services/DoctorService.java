package com.example.demo.services;

import com.example.demo.models.response.DoctorAdministrativeResponse;
import com.example.demo.models.response.DoctorResponse;
import com.example.demo.models.response.DoctorViewResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    List<DoctorViewResponse> getAllDoctorsInfo();

    List<DoctorViewResponse> getAllActiveDoctorsInfo();

    DoctorResponse getDoctorByUuid(UUID uuid);
    DoctorAdministrativeResponse getDetailedDoctorByUuid(UUID uuid);

    DoctorResponse getDoctorByAccountId(UUID uuid);

    DoctorResponse createDoctor(UUID accountId);

    void disableDoctor(UUID uuid);
}

