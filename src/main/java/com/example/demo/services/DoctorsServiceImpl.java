package com.example.demo.services;

import com.example.demo.models.Doctor;
import com.example.demo.models.response.DoctorResponse;
import com.example.demo.models.response.DoctorViewResponse;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorsServiceImpl implements DoctorService{

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<DoctorViewResponse> getAllDoctorsInfo() {
        return this.doctorRepository.findAll().stream()
                .map(doctor -> new DoctorViewResponse(doctor.getUuid(), doctor.getAccount().getFirstName(), doctor.getAccount().getLastName(), doctor.getSpecialty()))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponse getDoctorInfo(UUID uuid) {
        Doctor doctor = this.doctorRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("no such record!"));
        return new DoctorResponse(
                doctor.getUuid(),
                doctor.getAccount().getFirstName(),
                doctor.getAccount().getLastName(),
                doctor.getSpecialty());
    }
}
