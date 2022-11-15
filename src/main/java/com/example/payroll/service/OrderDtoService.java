package com.example.payroll.service;

import com.example.payroll.model.Order;
import com.example.payroll.model.OrderBase;
import com.example.payroll.model.Status;
import com.example.payroll.repository.OrderRepository;
import com.example.payroll.service.dto.OrderDisplayDTO;
import com.example.payroll.service.dto.OrderUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDtoService implements OrderService {

    private final OrderRepository.OrderNaturalKeyRepository repository;

    public OrderDtoService(OrderRepository.OrderNaturalKeyRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderDisplayDTO createOrder(OrderUpdateDTO updateDTO) {
        Optional<Order> res = fromUpdateDTO(updateDTO);
        if (res.isEmpty()) {
            Order savedOrder = repository.save(Order.builder()
                    .description(updateDTO.getDescription())
                    .status(Status.IN_PROGRESS)
                    .build());
            return toDisplayDTO(savedOrder);
        }
        return updateOrder(updateDTO)
                .orElseThrow();
    }

    @Override
    public Optional<OrderDisplayDTO> updateOrder(OrderUpdateDTO updateDTO) {
        Optional<Order> res = repository.findById(updateDTO.getCustomerOrderId());
        if (res.isEmpty()) {
            return Optional.empty();
        }
        Order order = res.get();
        order.setDescription(updateDTO.getDescription());
        Order updatedOrder = repository.save(order);
        return Optional.of(toDisplayDTO(updatedOrder));
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderBase.CustomerOrder id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Order> fromUpdateDTO(OrderUpdateDTO updateDTO) {
        return repository.findById(updateDTO.getCustomerOrderId())
                .map(order -> {
                    order.setDescription(updateDTO.getDescription());
                    return order;
                });
    }

    @Override
    public OrderDisplayDTO toDisplayDTO(Order order) {
        return OrderToAndFromDtoMapper.INSTANCE.toDisplayDto(order);
    }

    @Override
    public OrderDisplayDTO save(Order order) {
        return toDisplayDTO(
                repository.save(order)
        );
    }
}
