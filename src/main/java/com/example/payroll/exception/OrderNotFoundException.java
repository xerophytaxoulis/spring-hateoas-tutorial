package com.example.payroll.exception;

import com.example.payroll.model.OrderBase;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderBase.CustomerOrder id) {
        super(id.toString());
    }
}
