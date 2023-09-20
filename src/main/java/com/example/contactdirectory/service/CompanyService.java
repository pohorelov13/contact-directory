package com.example.contactdirectory.service;

import com.example.contactdirectory.exceptions.WrongIdException;
import com.example.contactdirectory.model.Company;
import com.example.contactdirectory.model.Person;
import com.example.contactdirectory.model.Phone;
import com.example.contactdirectory.repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo repo;


    public List<Company> getAllCompany() {
        return repo.findAll();
    }

    public Company addCompany(Company company) {
        return repo.save(company);
    }

    public Company deleteCompany(Long id) throws WrongIdException {
        Company company = repo.findById(id).orElseThrow(WrongIdException::new);
        repo.delete(company);
        return company;
    }
}
