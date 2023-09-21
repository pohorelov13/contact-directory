package com.example.contactdirectory;

import com.example.contactdirectory.exceptions.ValidationException;
import com.example.contactdirectory.model.Company;
import com.example.contactdirectory.model.Person;
import com.example.contactdirectory.model.Phone;
import com.example.contactdirectory.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static com.example.contactdirectory.util.Validator.checkIsEmpty;

@RequiredArgsConstructor
@Component
public class ConsoleInterface implements GuInterface {
    private final Scanner scanner;
    private final CompanyService service;


    public void render() {
        boolean isExit = false;
        System.out.println("КОНТАКТНИЙ ДОВІДНИК");
        while (!isExit) {
            System.out.print("""
                                    
                    Виберіть пункт меню:
                    1. Вивести список компаній
                    2. Додати компанію і її контактні дані
                    3. Видалити компанію і її контактні дані
                    4. Вихід
                    >""");
            String enteredCode = scanner.nextLine();
            System.out.println("-------------------------------------");
            switch (enteredCode) {
                case "1" -> showCompanies();
                case "2" -> createCompany();
                case "3" -> deleteCompany();
                case "4" -> isExit = true;
                default -> System.out.println("Помилкова команда");
            }
        }
        scanner.close();
    }

    private void deleteCompany() {
        System.out.print("Введіть ID компанії, яку треба видалити: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            try {
                Company company = service.deleteCompany(id);
                System.out.printf("\nКомпанія \"%s\" була видалена\n\n", company.getName());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Введіть коректний ID\n");
        }
    }

    private void createCompany() {
        Company company = new Company();

        System.out.print("Введіть назву: ");
        String name = scanner.nextLine();

        if (checkIsEmpty(name)) {
            System.out.println("Назва компанії обов'язкове поле!");
        } else {
            System.out.print("Введіть опис: ");
            String description = scanner.nextLine();

            company.setName(name);
            company.setDescription(description);

            getAddMenu(company);

            service.addCompany(company);
            System.out.println("Додана компанія\n");
            showCompanyDetails(company);
        }
    }

    private void getAddMenu(Company company) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("""
                                        
                    Виберіть меню:
                    1. Додати контактну особу
                    2. Додати номер телефону
                    3. Закінчити редагування
                    >""");

            String enteredCode = scanner.nextLine();

            switch (enteredCode) {
                case "1" -> createPerson(company);
                case "2" -> createPhone(company);
                case "3" -> isExit = true;
                default -> System.out.println("Неправильне значення");
            }
        }
    }

    private void createPhone(Company company) {
        Phone phone = new Phone();
        System.out.print("Введіть номер телефону: ");
        String phoneNumber = scanner.nextLine();

        phone.setPhoneNumber(phoneNumber);

        try {
            service.addPhone(company, phone);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
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

        try {
            service.addPerson(company, person);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }


    private void showCompanies() {
        List<Company> allCompany = service.getAllCompany();
        if (allCompany.isEmpty()) {
            System.out.println("Ще не додано жодної компанії");
        } else {
            for (Company company : allCompany) {
                showCompanyDetails(company);
            }
        }
    }

    private void showCompanyDetails(Company company) {
        System.out.printf("""
                ID: %d
                Найменування: %s
                Опис: %s
                                
                Контактні особи:
                """, company.getId(), company.getName(), company.getDescription().orElse(""));
        company.getContactPersons().forEach(p -> System.out.println("* " + p.getFullName()));


        System.out.println("\nТелефони:");

        for (Phone p : company.getPhoneList()) {
            System.out.println("* " + p.getPhoneNumber());
        }
        System.out.println("--------------------------------------------");
    }
}
