package com.example.contactdirectory.model;

import com.example.contactdirectory.util.Validator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;

@Entity
@Data
@Table(name = "persons")
@ToString(exclude = "company")
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


    public void setFatherName(String fatherName) {
        this.fatherName = Validator.checkIsEmpty(fatherName) ? null : fatherName;
    }

    //helper method
    public void addCompany(Company company) {
        this.company = company;
        company.getContactPersons().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return Objects.equals(lastName, person.lastName)
                && Objects.equals(firstName, person.firstName)
                && Objects.equals(id, person.getId())
                && Objects.equals(fatherName, person.fatherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lastName, firstName, fatherName);
    }
}

