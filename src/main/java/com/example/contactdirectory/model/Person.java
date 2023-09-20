package com.example.contactdirectory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
public class Person extends BaseEntity {
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String firstName;
    private String fatherName;
    @ManyToOne()
    @JoinColumn(name = "company_id")
    private Company company;

    public String getFullName() {
        return lastName + " " + firstName + " " + getFatherName().orElse("");
    }

    public Optional<String> getFatherName() {
        return Optional.ofNullable(fatherName);
    }
}
