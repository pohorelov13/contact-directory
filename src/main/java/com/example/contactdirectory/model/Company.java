package com.example.contactdirectory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Person> contactPersons = new ArrayList<>();
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phoneList = new ArrayList<>();

}