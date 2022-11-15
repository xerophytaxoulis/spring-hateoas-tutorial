package com.example.payroll.service;

import com.example.payroll.model.Order;
import com.example.payroll.model.OrderBase;
import com.example.payroll.service.dto.OrderDisplayDTO;
import com.example.payroll.service.dto.OrderUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderDisplayDTO createOrder(OrderUpdateDTO updateDTO);

    Optional<OrderDisplayDTO> updateOrder(OrderUpdateDTO updateDTO);

    List<Order> findAll();

    Optional<Order> findById(OrderBase.CustomerOrder id);

    Optional<Order> fromUpdateDTO(OrderUpdateDTO updateDTO);

    OrderDisplayDTO toDisplayDTO(Order order);

    OrderDisplayDTO save(Order order);
}
