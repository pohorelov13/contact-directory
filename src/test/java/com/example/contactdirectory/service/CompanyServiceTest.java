package com.example.contactdirectory.service;

import com.example.contactdirectory.exceptions.ValidationException;
import com.example.contactdirectory.model.Company;
import com.example.contactdirectory.model.Person;
import com.example.contactdirectory.model.Phone;
import com.example.contactdirectory.repo.CompanyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyService.class})
class CompanyServiceTest {
    @MockBean
    private CompanyRepo repo;

    @MockBean
    private PhoneService phoneService;

    @Autowired
    private CompanyService service;

    private Company company;
    private Person person;
    private Phone phone;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setName("Test");

        phone = new Phone();
        phone.setPhoneNumber("+380999283122");

        person = new Person();
        person.setFirstName("Bohdan");
        person.setLastName("Pohorelov");

    }

    /**
     * Method under test: {@link CompanyService#getAllCompany()}
     */


    @Test
    void getAllCompanyTest() {
        when(repo.findAllCompaniesWithPersonsAndPhones()).thenReturn(new ArrayList<>());
        service.getAllCompany();
        verify(repo, times(1)).findAllCompaniesWithPersonsAndPhones();
    }

    /**
     * Method under test: {@link CompanyService#addCompany(Company)}
     */

    @Test
    void addCompanyWhenNameNotEmptyTest() throws ValidationException {
        when(repo.save(company)).thenReturn(company);
        Company addedCompany = service.addCompany(company);
        verify(repo, times(1)).save(company);
        assertEquals(company, addedCompany);
    }

    /**
     * Method under test: {@link CompanyService#addCompany(Company)}
     */

    @Test
    void addCompanyWhenNameEmptyTest() {

        company.setName("");

        ValidationException exception = assertThrows(ValidationException.class, () -> service.addCompany(company));
        assertEquals("Назва компанії обов'язкове поле!", exception.getMessage());
        verify(repo, times(0)).save(any());

    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void deleteCompanyWhenExistTest() throws ValidationException {

        company.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(company));
        doNothing().when(repo).delete(company);

        Company deletedCompany = service.deleteCompany(1L);

        assertEquals(company, deletedCompany);
    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void deleteCompanyWhenNotExistTest() {

        company.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.empty());

        ValidationException r = assertThrows(ValidationException.class, () -> service.deleteCompany(1L));
        assertEquals(r.getMessage(), "Компанії з даним ID не існує");
        verify(repo, times(0)).delete(any());
    }

    /**
     * Method under test: {@link CompanyService#addPerson(Company, Person)}
     */

    @Test
    void addPersonWhenFirstNameAndLastNameNotEmpty() throws ValidationException {

        service.addPerson(company, person);

        assertEquals(company.getContactPersons().stream().findFirst().orElse(new Person()), person);
        assertEquals(person.getCompany(), company);

    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void addPersonWhenFirstNameEmptyAndLastNameNotEmptyTest() {

        person.setFirstName("");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> service.addPerson(company, person));

        assertEquals("Прізвище та ім'я обов'язкові поля!", exception.getMessage());
    }

    /**
     * Method under test: {@link CompanyService#addPhone(Company, Phone)}
     */

    @Test
    void addPhoneWhenNumberValidAndNotExistInDB() throws ValidationException {

        when(phoneService.isPhoneExist(phone.getPhoneNumber())).thenReturn(false);
        service.addPhone(company, phone);

        assertEquals(phone, company.getPhoneList().stream().findFirst().orElse(new Phone()));
        assertEquals(company, phone.getCompany());
    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void addPhoneWhenNumberValidExistInDB() {

        checkMessage(phone, company, "Номер телефону вже існує", true);

    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void addPhoneWhenNumberIsEmpty() {

        phone.setPhoneNumber("");

        checkMessage(phone, company, "Номер телефону не може бути пустим", false);

    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void addPhoneWhenNumberIsNotValid() {

        phone.setPhoneNumber("number");

        checkMessage(phone, company, "Помилковий формат номеру", false);

    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */

    @Test
    void addPhoneWhenNumberIsStartWithZero() {
        phone.setPhoneNumber("0999283122");
        checkMessage(phone, company, "Помилковий формат номеру", false);

    }

    private void checkMessage(Phone phone, Company company, String expected, boolean isExist) {
        when(phoneService.isPhoneExist(phone.getPhoneNumber())).thenReturn(isExist);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> service.addPhone(company, phone));
        assertEquals(expected, exception.getMessage());

        assertEquals(company.getPhoneList().size(), 0);
        assertNull(phone.getCompany());
    }
}