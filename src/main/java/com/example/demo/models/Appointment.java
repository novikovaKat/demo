package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name = "appointment")
public class Appointment {
    @Id
    @Column (name = "uuid")
    private UUID uuid;
    @Column (name = "patient_id")
    private UUID patientId;
    @Column (name = "doctor_id")
    private UUID doctorId;
    @Column (name = "appointment_date")
    private Instant appointmentDate;
    @Column (name = "status")
    private Statuses status;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public Instant getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Instant appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return uuid.equals(that.uuid) && patientId.equals(that.patientId) && doctorId.equals(that.doctorId) && appointmentDate.equals(that.appointmentDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, patientId, doctorId, appointmentDate, status);
    }
}
