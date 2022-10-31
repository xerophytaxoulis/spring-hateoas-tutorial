package com.example.payroll.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super(id.toString());
    }
}
