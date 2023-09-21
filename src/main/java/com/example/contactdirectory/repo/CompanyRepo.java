package com.example.contactdirectory.repo;

import com.example.contactdirectory.model.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

    @Query("select c from Company c left join fetch c.contactPersons cp left join fetch c.phoneList p")
    List<Company> findAll();
}
