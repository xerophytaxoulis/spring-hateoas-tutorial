package com.example.payroll.service.dto;

import com.example.payroll.model.OrderBase;
import lombok.Data;

@Data
public class OrderUpdateDTO extends OrderBase {
    private String description;
}
