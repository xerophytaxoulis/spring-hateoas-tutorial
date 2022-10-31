package com.example.payroll.controller;

import com.example.payroll.exception.OrderNotFoundException;
import com.example.payroll.model.Order;
import com.example.payroll.model.Status;
import com.example.payroll.repository.OrderRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repository;

    private final OrderModelAssembler assembler;

    public OrderController(OrderRepository repository, OrderModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    CollectionModel<EntityModel<Order>> all() {
        var orders = repository.findAll()
                .stream().map(assembler::toModel).toList();
        return CollectionModel.of(
                orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    EntityModel<Order> one(@PathVariable Long id) {
        return repository.findById(id)
                .map(assembler::toModel)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {
        order.setStatus(Status.IN_PROGRESS);
        // var newOrder = repository.save(order);
        // return ResponseEntity
        //         .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
        //         .body(assembler.toModel(newOrder));
        var em = assembler.toModel(repository.save(order));
        return ResponseEntity
                .created(em.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(em);
    }


    @PutMapping("/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        var order = repository.findById(id).orElseThrow(()
                -> new OrderNotFoundException(id));
        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed here")
                        .withDetail("You can not complete an order that is in the " + order.getStatus() +
                                " status."));
    }

    @DeleteMapping("/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        var order = repository.findById(id).orElseThrow(()
                -> new OrderNotFoundException(id));
        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }

        // return ResponseEntity
        //         .status(HttpStatus.METHOD_NOT_ALLOWED)
        //         .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        //         .body(Problem.create()
        //                 .withTitle("Method not allowed here")
        //                 .withDetail("You can not cancel an order that is in the " + order.getStatus() +
        //                         " status."));

        return ResponseEntity
                .of(ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED,
                        "You can not cancel an order that is in the "
                                + order.getStatus() + " status."))
                .build();
    }
}
