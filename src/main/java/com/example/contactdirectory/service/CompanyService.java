package com.example.contactdirectory.service;

import com.example.contactdirectory.exceptions.ValidationException;
import com.example.contactdirectory.model.Company;
import com.example.contactdirectory.model.Person;
import com.example.contactdirectory.model.Phone;
import com.example.contactdirectory.repo.CompanyRepo;
import com.example.contactdirectory.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.contactdirectory.util.Validator.checkIsEmpty;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo repo;
    private final PhoneService phoneService;


    //get all companies with persons and phone
    public List<Company> getAllCompany() {
        return repo.findAllCompaniesWithPersonsAndPhones();
    }

    public Company addCompany(Company company) throws ValidationException {
        if (checkIsEmpty(company.getName())) {
            throw new ValidationException("Назва компанії обов'язкове поле!");
        } else return repo.save(company);
    }

    public Company deleteCompany(Long id) throws ValidationException {
        Company company = repo.findById(id).orElseThrow(() -> new ValidationException("Компанії з даним ID не існує"));
        repo.delete(company);
        return company;
    }

    public void addPhone(Company company, Phone phone) throws ValidationException {
        String phoneNumber = phone.getPhoneNumber();
        if (checkIsEmpty(phoneNumber)) {
            throw new ValidationException("Номер телефону не може бути пустим");
        } else if (!Validator.checkPhone(phoneNumber)) { //check is phone already exist in DB
            throw new ValidationException("Помилковий формат номеру");
        } else if (phoneService.isPhoneExist(phoneNumber)) {
            throw new ValidationException("Номер телефону вже існує");
            //check format
        } else phone.addCompany(company);
    }

    public void addPerson(Company company, Person person) throws ValidationException {
        if (checkIsEmpty(person.getLastName(), person.getFirstName())) {
            throw new ValidationException("Прізвище та ім'я обов'язкові поля!");
        } else {
            person.addCompany(company);
        }
    }
}
