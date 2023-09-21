package com.example.contactdirectory;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class ContactDirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactDirectoryApplication.class, args);
    }
}
