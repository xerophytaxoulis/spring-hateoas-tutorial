package com.example.payroll.controller;

import com.example.payroll.model.Status;
import com.example.payroll.service.dto.OrderDisplayDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler
        implements RepresentationModelAssembler<OrderDisplayDTO, EntityModel<OrderDisplayDTO>> {
    @Override
    public EntityModel<OrderDisplayDTO> toModel(OrderDisplayDTO entity) {
        var em = EntityModel
                .of(entity,
                        linkTo(methodOn(OrderController.class).one(entity.getCustomerOrderId())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).all()).withRel("orders")
                );
        if (entity.getStatus() == Status.IN_PROGRESS) {
            em.add(linkTo(methodOn(OrderController.class).complete(entity.getCustomerOrderId())).withRel("complete"));
            em.add(linkTo(methodOn(OrderController.class).cancel(entity.getCustomerOrderId())).withRel("cancel"));
        }
        return em;
    }
}
