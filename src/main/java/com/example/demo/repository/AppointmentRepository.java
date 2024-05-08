package com.example.demo.repository;

import com.example.demo.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.uuid = :doctorId AND a.appointmentDate >= :startUtc AND a.appointmentDate < :endUtc")
    List<Appointment> findAllByDoctorIdInInterval(
            @Param("doctorId") final UUID doctorId,
            @Param("startUtc") final Instant startUtc,
            @Param("endUtc") final Instant endUtc);
}
