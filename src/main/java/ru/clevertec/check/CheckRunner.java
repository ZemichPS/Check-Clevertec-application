package ru.clevertec.check;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.clevertec.check.application.ports.output.*;

@SpringBootApplication
public class CheckRunner {
    public static void main(String[] args) {
        SpringApplication.run(CheckRunner.class, args);
    }

}
