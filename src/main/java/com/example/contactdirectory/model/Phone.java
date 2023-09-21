package com.example.contactdirectory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phones")
public class Phone extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public void addCompany(Company company) {
        this.company = company;
        company.getPhoneList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Phone phone = (Phone) o;
        return Objects.equals(phoneNumber, phone.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber);
    }
}