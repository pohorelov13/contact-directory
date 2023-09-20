package com.example.contactdirectory;

import com.example.contactdirectory.exceptions.WrongIdException;
import com.example.contactdirectory.model.Company;
import com.example.contactdirectory.model.Person;
import com.example.contactdirectory.model.Phone;
import com.example.contactdirectory.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class ConsoleInterface implements GuInterface {
    private final Scanner scanner;
    private final CompanyService service;


    public void render() {
        System.out.println("КОНТАКТНИЙ ДОВІДНИК");
        while (true) {
            System.out.print("""                
                    Виберіть пункт меню:
                    1. Вивести список компаній
                    2. Додати компанію і її контактні дані
                    3. Видалити компанію і її контактні дані
                                        
                    > """);
            String enteredCode = scanner.nextLine();

            switch (enteredCode) {
                case "1" -> showCompanies();
                case "2" -> createCompany();
                case "3" -> deleteCompany();
            }

        }
    }

    private void deleteCompany() {
        System.out.print("Введіть ID компанії, яку треба видалити:");
        long id = scanner.nextLong();
        try {
            Company company = service.deleteCompany(id);
            System.out.printf("Компанія \"%s\" була видалена\n", company.getName());
        } catch (WrongIdException e) {
            System.out.println(e.getMessage());
        }

    }

    private void createCompany() {
        Company company = new Company();

        System.out.print("Введіть назву: ");
        String name = scanner.nextLine();
        System.out.print("Введіть опис: ");
        String description = scanner.nextLine();

        company.setName(name);
        company.setDescription(description);
        boolean isExit = false;
        while (!isExit) {
            System.out.println("""
                                        
                    Виберіть меню:
                    1. Додати контактну особу
                    2. Додати номер телефону
                    3. Закінчити редагування
                    """);

            String enteredCode = scanner.nextLine();

            switch (enteredCode) {
                case "1" -> createPerson(company);
                case "2" -> createPhone(company);
                case "3" -> isExit = true;
                default -> System.out.println("Неправильне значення");
            }
        }
        service.addCompany(company);
        System.out.println("Додана компанія");
        showCompanyDetails(company);

    }

    private void createPhone(Company company) {
        Phone phone = new Phone();
        System.out.print("Введіть номер телефону: ");
        String phoneNumber = scanner.nextLine();
        phone.setPhoneNumber(phoneNumber);
        company.getPhoneList().add(phone);
        phone.setCompany(company);
    }

    private void createPerson(Company company) {
        Person person = new Person();
        System.out.print("Введіть прізвище: ");
        String lastName = scanner.nextLine();
        System.out.print("Введіть ім'я: ");
        String firstName = scanner.nextLine();
        System.out.print("Введіть по-батькові: ");
        String fatherName = scanner.nextLine();
        person.setLastName(lastName);
        person.setFirstName(firstName);
        person.setFatherName(fatherName);
        person.setCompany(company);
        company.getContactPersons().add(person);

    }

    private void showCompanies() {
        List<Company> allCompany = service.getAllCompany();

        for (Company company : allCompany) {
            showCompanyDetails(company);
        }
    }

    private void showCompanyDetails(Company company) {
        System.out.printf("""
                ID: %d
                Найменування: %s
                Опис: %s
                                
                Контактні особи:
                                
                """, company.getId(), company.getName(), company.getDescription());
        for (Person p : company.getContactPersons()) {
            System.out.println("* " + p.getFullName());
        }
        System.out.println();
        System.out.println("Телефони:");

        for (Phone p : company.getPhoneList()) {
            System.out.println("* " + p.getPhoneNumber());
        }
        System.out.println();
    }
}
