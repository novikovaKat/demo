package com.example.demo.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table (name = "doctor")
public class Doctor{
    @Id
    @Column (name = "uuid")
    private UUID uuid;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column (name = "specialty")
    private String specialty;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return uuid.equals(doctor.uuid) && account.equals(doctor.account) && specialty.equals(doctor.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, account, specialty);
    }
}
