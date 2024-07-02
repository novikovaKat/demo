package com.example.demo.repository;

import com.example.demo.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Optional<Doctor> findByAccountUuid(UUID uuid);

    List<Doctor> findByStatusIsTrue();
}

