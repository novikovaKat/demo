package com.example.demo.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "specialty")
public class Specialty {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "specialties")
    private Set<Doctor> doctors = new HashSet<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialty)) return false;
        Specialty specialty = (Specialty) o;
        return uuid.equals(specialty.uuid) && name.equals(specialty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }
}
