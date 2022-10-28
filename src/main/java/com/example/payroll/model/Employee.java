package com.example.payroll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long Id;

    private String name;

    private String role;

    public Employee() {
    }

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(Id, employee.Id) && Objects.equals(name, employee.name) && Objects.equals(role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, role);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
