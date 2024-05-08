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

    @Column(name = "education")
    private String education;

    @Column(name = "experience")
    private String experience;

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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return uuid.equals(doctor.uuid) && account.equals(doctor.account) && Objects.equals(education, doctor.education) && Objects.equals(experience, doctor.experience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, account, education, experience);
    }
}
