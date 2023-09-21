package com.example.contactdirectory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.contactdirectory.exceptions.ValidationException;
import com.example.contactdirectory.model.Company;
import com.example.contactdirectory.model.Person;
import com.example.contactdirectory.model.Phone;
import com.example.contactdirectory.repo.CompanyRepo;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CompanyService.class})
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {
    @MockBean
    private CompanyRepo companyRepo;

    @Autowired
    private CompanyService companyService;

    @MockBean
    private PhoneService phoneService;

    /**
     * Method under test: {@link CompanyService#getAllCompany()}
     */
    @Test
    void testGetAllCompany() {
        ArrayList<Company> companyList = new ArrayList<>();
        when(companyRepo.findAll()).thenReturn(companyList);
        List<Company> actualAllCompany = companyService.getAllCompany();
        assertSame(companyList, actualAllCompany);
        assertTrue(actualAllCompany.isEmpty());
        verify(companyRepo).findAll();
    }

    /**
     * Method under test: {@link CompanyService#addCompany(Company)}
     */
    @Test
    void testAddCompany() {
        Company company = new Company();
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");
        when(companyRepo.save(Mockito.<Company>any())).thenReturn(company);

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");
        assertSame(company, companyService.addCompany(company2));
        verify(companyRepo).save(Mockito.<Company>any());
    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */
    @Test
    void testDeleteCompany() throws ValidationException {
        Company company = new Company();
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");
        Optional<Company> ofResult = Optional.of(company);
        doNothing().when(companyRepo).delete(Mockito.<Company>any());
        when(companyRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(company, companyService.deleteCompany(1L));
        verify(companyRepo).findById(Mockito.<Long>any());
        verify(companyRepo).delete(Mockito.<Company>any());
    }

    /**
     * Method under test: {@link CompanyService#deleteCompany(Long)}
     */
    @Test
    void testDeleteCompany2() throws ValidationException {
        Optional<Company> emptyResult = Optional.empty();
        when(companyRepo.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ValidationException.class, () -> companyService.deleteCompany(1L));
        verify(companyRepo).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CompanyService#addPhone(Company, Phone)}
     */
    @Test
    void testAddPhone() throws ValidationException {
        when(phoneService.isPhoneExist(Mockito.<String>any())).thenReturn(true);

        Company company = new Company();
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");

        Phone phone = new Phone();
        phone.setCompany(company2);
        phone.setId(1L);
        phone.setPhoneNumber("6625550144");
        assertThrows(ValidationException.class, () -> companyService.addPhone(company, phone));
        verify(phoneService).isPhoneExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CompanyService#addPhone(Company, Phone)}
     */
    @Test
    void testAddPhone2() throws ValidationException {
        when(phoneService.isPhoneExist(Mockito.<String>any())).thenReturn(false);

        Company company = new Company();
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");

        Phone phone = new Phone();
        phone.setCompany(company2);
        phone.setId(1L);
        phone.setPhoneNumber("6625550144");
        companyService.addPhone(company, phone);
        verify(phoneService).isPhoneExist(Mockito.<String>any());
        assertEquals(1, company.getPhoneList().size());
        assertSame(company, phone.getCompany());
    }

    /**
     * Method under test: {@link CompanyService#addPhone(Company, Phone)}
     */
    @Test
    void testAddPhone3() throws ValidationException {
        Company company = new Company();
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");
        Phone phone = mock(Phone.class);
        when(phone.getPhoneNumber()).thenReturn("");
        doNothing().when(phone).setId(Mockito.<Long>any());
        doNothing().when(phone).setCompany(Mockito.<Company>any());
        doNothing().when(phone).setPhoneNumber(Mockito.<String>any());
        phone.setCompany(company2);
        phone.setId(1L);
        phone.setPhoneNumber("6625550144");
        assertThrows(ValidationException.class, () -> companyService.addPhone(company, phone));
        verify(phone).getPhoneNumber();
        verify(phone).setId(Mockito.<Long>any());
        verify(phone).setCompany(Mockito.<Company>any());
        verify(phone).setPhoneNumber(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CompanyService#addPerson(Company, Person)}
     */
    @Test
    void testAddPerson() throws ValidationException {
        Company company = new Company();
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");

        Person person = new Person();
        person.setCompany(company2);
        person.setFatherName("Father Name");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        companyService.addPerson(company, person);
        assertEquals(1, company.getContactPersons().size());
        assertSame(company, person.getCompany());
    }

    /**
     * Method under test: {@link CompanyService#addPerson(Company, Person)}
     */
    @Test
    void testAddPerson2() throws ValidationException {
        Company company = mock(Company.class);
        when(company.getContactPersons()).thenReturn(new HashSet<>());
        doNothing().when(company).setId(Mockito.<Long>any());
        doNothing().when(company).setDescription(Mockito.<String>any());
        doNothing().when(company).setName(Mockito.<String>any());
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");

        Person person = new Person();
        person.setCompany(company2);
        person.setFatherName("Father Name");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        companyService.addPerson(company, person);
        verify(company).getContactPersons();
        verify(company).setId(Mockito.<Long>any());
        verify(company).setDescription(Mockito.<String>any());
        verify(company).setName(Mockito.<String>any());
        assertSame(company, person.getCompany());
    }

    /**
     * Method under test: {@link CompanyService#addPerson(Company, Person)}
     */
    @Test
    void testAddPerson3() throws ValidationException {
        Company company = mock(Company.class);
        doNothing().when(company).setId(Mockito.<Long>any());
        doNothing().when(company).setDescription(Mockito.<String>any());
        doNothing().when(company).setName(Mockito.<String>any());
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");
        Person person = mock(Person.class);
        when(person.getFirstName()).thenReturn("");
        when(person.getLastName()).thenReturn("Doe");
        doNothing().when(person).setId(Mockito.<Long>any());
        doNothing().when(person).setCompany(Mockito.<Company>any());
        doNothing().when(person).setFatherName(Mockito.<String>any());
        doNothing().when(person).setFirstName(Mockito.<String>any());
        doNothing().when(person).setLastName(Mockito.<String>any());
        person.setCompany(company2);
        person.setFatherName("Father Name");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        assertThrows(ValidationException.class, () -> companyService.addPerson(company, person));
        verify(company).setId(Mockito.<Long>any());
        verify(company).setDescription(Mockito.<String>any());
        verify(company).setName(Mockito.<String>any());
        verify(person).getFirstName();
        verify(person).getLastName();
        verify(person).setId(Mockito.<Long>any());
        verify(person).setCompany(Mockito.<Company>any());
        verify(person).setFatherName(Mockito.<String>any());
        verify(person).setFirstName(Mockito.<String>any());
        verify(person).setLastName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CompanyService#addPerson(Company, Person)}
     */
    @Test
    void testAddPerson4() throws ValidationException {
        Company company = mock(Company.class);
        doNothing().when(company).setId(Mockito.<Long>any());
        doNothing().when(company).setDescription(Mockito.<String>any());
        doNothing().when(company).setName(Mockito.<String>any());
        company.setDescription("The characteristics of someone or something");
        company.setId(1L);
        company.setName("Name");

        Company company2 = new Company();
        company2.setDescription("The characteristics of someone or something");
        company2.setId(1L);
        company2.setName("Name");
        Person person = mock(Person.class);
        when(person.getLastName()).thenReturn("");
        doNothing().when(person).setId(Mockito.<Long>any());
        doNothing().when(person).setCompany(Mockito.<Company>any());
        doNothing().when(person).setFatherName(Mockito.<String>any());
        doNothing().when(person).setFirstName(Mockito.<String>any());
        doNothing().when(person).setLastName(Mockito.<String>any());
        person.setCompany(company2);
        person.setFatherName("Father Name");
        person.setFirstName("Jane");
        person.setId(1L);
        person.setLastName("Doe");
        assertThrows(ValidationException.class, () -> companyService.addPerson(company, person));
        verify(company).setId(Mockito.<Long>any());
        verify(company).setDescription(Mockito.<String>any());
        verify(company).setName(Mockito.<String>any());
        verify(person).getLastName();
        verify(person).setId(Mockito.<Long>any());
        verify(person).setCompany(Mockito.<Company>any());
        verify(person).setFatherName(Mockito.<String>any());
        verify(person).setFirstName(Mockito.<String>any());
        verify(person).setLastName(Mockito.<String>any());
    }
}

