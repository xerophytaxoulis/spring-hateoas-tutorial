package com.example.payroll.bootstrap;

import com.example.payroll.model.Employee;
import com.example.payroll.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
       return args -> List.of(
               new Employee("Bilbo", "Baggins", "burglar"),
               new Employee("Frodo", "Baggins", "thief"))
               .forEach(employee -> LOGGER.info("Preloading {}", repository.save(employee)));
    }
}
