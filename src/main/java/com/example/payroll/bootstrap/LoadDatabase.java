package com.example.payroll.bootstrap;

import com.example.payroll.model.Employee;
import com.example.payroll.model.Order;
import com.example.payroll.model.Status;
import com.example.payroll.repository.EmployeeRepository;
import com.example.payroll.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository,
                                   OrderRepository.OrderIdRepository orderRepository) {
       return args -> {
           List.of(
                   new Employee("Bilbo", "Baggins", "burglar"),
                   new Employee("Frodo", "Baggins", "thief")
           ).forEach(employee -> log.info("Preloading {}", employeeRepository.save(employee)));

           List.of(
                   new Order("iPhone", Status.IN_PROGRESS),
                   new Order("MacBook Pro", Status.COMPLETED)
           ).forEach(order -> log.info("Preloading {}", orderRepository.save(order)));
       };
    }
}
