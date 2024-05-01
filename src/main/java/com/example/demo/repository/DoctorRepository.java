package com.example.demo.repository;

import com.example.demo.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}
