package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return uuid.equals(offer.uuid) && name.equals(offer.name) && price.equals(offer.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, price);
    }
}
