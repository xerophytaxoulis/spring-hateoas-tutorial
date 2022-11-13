package com.example.payroll.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public class OrderBase {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private CustomerOrder customerOrderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBase orderBase = (OrderBase) o;
        return Objects.equals(id, orderBase.id) || Objects.equals(customerOrderId, orderBase.customerOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Objects.hash(id), Objects.hash(customerOrderId));
    }

    @IdClass(OrderBase.class)
    @Data
    @NoArgsConstructor
    public static class CustomerOrder implements Serializable {
        @Id
        private String customerAccountName;
        @Id
        private Long productSerialNumber;
    }
}
