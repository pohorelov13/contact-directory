package com.example.contactdirectory.config;

import com.example.contactdirectory.GUI.GuInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final GuInterface consoleInterface;

    @Override
    public void run(String... args) throws Exception {
        consoleInterface.start();
    }
}
