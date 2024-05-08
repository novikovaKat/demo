package com.example.demo.models;

import com.example.demo.models.enums.AppointmentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name = "appointment")
public class Appointment {
    @Id
    @Column (name = "uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Account patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column (name = "appointment_date")
    private Instant appointmentDate;
    @Column (name = "status")
    private AppointmentStatus status;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Account getPatient() {
        return patient;
    }

    public void setPatient(Account patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Instant getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Instant appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(patient, that.patient)
                && Objects.equals(doctor, that.doctor)
                && Objects.equals(appointmentDate, that.appointmentDate)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, patient, doctor, appointmentDate, status);
    }
}
