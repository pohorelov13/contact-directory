package com.example.contactdirectory.repo;

import com.example.contactdirectory.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepo extends JpaRepository<Phone, Long> {

}
