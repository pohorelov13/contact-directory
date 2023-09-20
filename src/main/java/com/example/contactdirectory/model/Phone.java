package com.example.contactdirectory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phone extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}