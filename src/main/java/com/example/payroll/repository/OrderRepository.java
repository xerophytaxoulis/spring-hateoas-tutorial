package com.example.payroll.repository;

import com.example.payroll.model.Order;
import com.example.payroll.model.OrderBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository<ID> extends JpaRepository<Order, ID> {
    interface OrderIdRepository extends OrderRepository<Long> {}
    interface OrderNaturalKeyRepository extends OrderRepository<OrderBase.CustomerOrder> {}
}
