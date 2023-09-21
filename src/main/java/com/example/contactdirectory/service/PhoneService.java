package com.example.contactdirectory.service;

import com.example.contactdirectory.repo.PhoneRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepo repo;

    public boolean isPhoneExist(String phoneNumber) {
        return repo.existsByPhoneNumber(phoneNumber);
    }
}
