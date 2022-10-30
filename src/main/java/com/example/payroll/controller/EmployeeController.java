package com.example.payroll.controller;

import com.example.payroll.exception.EmployeeNotFoundException;
import com.example.payroll.model.Employee;
import com.example.payroll.repository.EmployeeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping
    CollectionModel<EntityModel<Employee>> all() {
        return CollectionModel.of(assembler.toCollectionModel(repository.findAll()),
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
        var em = assembler.toModel(repository.save(newEmployee));
        return ResponseEntity
                .created(em.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(em);
    }

    @GetMapping("/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        var employee = repository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException(id)
        );

        return assembler.toModel(employee);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        var em = assembler.toModel(repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                            newEmployee.setId(id);
                            return repository.save(newEmployee);
                        }
                ));

        return ResponseEntity
                .created(em.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(em);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }
}
