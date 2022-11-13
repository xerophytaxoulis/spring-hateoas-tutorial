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
    public Order createNewOrder(OrderUpdateDTO updateDTO) {
        Order order = fromUpdateDTO(updateDTO);
        order.setStatus(Status.IN_PROGRESS);
        return order;
    }

    @Override
    public List<OrderDisplayDTO> findAll() {
        return repository.findAll().stream().map(this::toDisplayDTO).toList();
    }

    @Override
    public Optional<OrderDisplayDTO> findById(OrderBase.CustomerOrder id) {
        return repository.findById(id).map(this::toDisplayDTO);
    }

    @Override
    public Order fromUpdateDTO(OrderUpdateDTO updateDTO) {
        return OrderToAndFromDtoMapper.INSTANCE.fromUpdateDto(updateDTO);
    }

    @Override
    public OrderDisplayDTO toDisplayDTO(Order order) {
        return OrderToAndFromDtoMapper.INSTANCE.toDisplayDto(order);
    }

    @Override
    public OrderDisplayDTO save(Order order) {
        return toDisplayDTO(repository.save(order));
    }

    @Override
    public List<OrderDisplayDTO> saveAll(List<Order> orders) {
        List<Order> savedOrders = repository.saveAll(orders);
        return savedOrders.stream().map(this::toDisplayDTO).toList();
    }

    @Override
    public Optional<OrderDisplayDTO> findByNaturalKey(OrderBase.CustomerOrder customerOrder) {
        return repository.findById(customerOrder).map(this::toDisplayDTO);
    }
}
