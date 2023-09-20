package com.example.contactdirectory.config;

import com.example.contactdirectory.ConsoleInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReconTool implements CommandLineRunner {
    private final ConsoleInterface consoleInterface;

    @Override
    public void run(String... args) throws Exception {
        consoleInterface.render();
    }
}
