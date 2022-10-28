package com.example.payroll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue
    private Long Id;

    private String firstName;

    private String lastName;

    private String role;

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getName() {
        return String.join(" ", firstName, lastName);
    }

    public void setName(String name) {
        String[] nameParts = name.split(" ");
        firstName = nameParts[0];
        lastName = nameParts[1];
    }
}
