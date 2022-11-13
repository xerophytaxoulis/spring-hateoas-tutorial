package com.example.payroll.service;

import com.example.payroll.model.Order;
import com.example.payroll.service.dto.OrderDisplayDTO;
import com.example.payroll.service.dto.OrderUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderToAndFromDtoMapper {
    OrderToAndFromDtoMapper INSTANCE = Mappers.getMapper(OrderToAndFromDtoMapper.class);
    OrderDisplayDTO toDisplayDto(Order order);
    Order fromUpdateDto(OrderUpdateDTO update);
}
