package com.example.payroll.service;

import com.example.payroll.model.Order;
import com.example.payroll.model.OrderBase;
import com.example.payroll.service.dto.OrderDisplayDTO;
import com.example.payroll.service.dto.OrderUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order createNewOrder(OrderUpdateDTO updateDTO);

    List<OrderDisplayDTO> findAll();

    Optional<OrderDisplayDTO> findById(OrderBase.CustomerOrder id);

    Order fromUpdateDTO(OrderUpdateDTO updateDTO);

    OrderDisplayDTO toDisplayDTO(Order order);

    OrderDisplayDTO save(Order order);

    List<OrderDisplayDTO> saveAll(List<Order> orders);

    Optional<OrderDisplayDTO> findByNaturalKey(OrderBase.CustomerOrder customerOrder);

}
