package com.example.demo.models;

import jakarta.persistence.*;

import java.util.*;

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

    @Column(name = "status")
    private Boolean status;

    @Column(name = "specialty")
    private String specialty;

    @ManyToMany
    @JoinTable(
            name = "doctor_specialty",
            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "uuid"))
    private Set<Specialty> specialties = new HashSet<>();

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return uuid.equals(doctor.uuid) && account.equals(doctor.account) && Objects.equals(education, doctor.education) && Objects.equals(experience, doctor.experience) && status.equals(doctor.status) && Objects.equals(specialties, doctor.specialties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, account, education, experience, status, specialties);
    }
}
