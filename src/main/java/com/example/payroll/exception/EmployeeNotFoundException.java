package com.example.payroll.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Could no find employee with id " + id);
    }
}
