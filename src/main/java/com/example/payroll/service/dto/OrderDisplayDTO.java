package com.example.payroll.service.dto;

import com.example.payroll.model.OrderBase;
import com.example.payroll.model.Status;
import lombok.Data;

@Data
public class OrderDisplayDTO extends OrderBase {
    private String description;
    private Status status;
}
