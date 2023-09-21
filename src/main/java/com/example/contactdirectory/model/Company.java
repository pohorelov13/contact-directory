package com.example.contactdirectory.model;

import com.example.contactdirectory.util.Validator;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "companies")
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Person> contactPersons = new HashSet<>();
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Phone> phoneList = new HashSet<>();

    public void setDescription(String description) {
        this.description = Validator.checkIsEmpty(description) ? null : description;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }


    private void setContactPersons(Set<Person> contactPersons) {
        this.contactPersons = contactPersons;
    }

    private void setPhoneList(Set<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(description, company.description) && Objects.equals(contactPersons, company.contactPersons) && Objects.equals(phoneList, company.phoneList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, contactPersons, phoneList);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}