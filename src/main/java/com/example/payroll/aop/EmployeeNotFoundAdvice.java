package com.example.payroll.aop;

import com.example.payroll.exception.EmployeeNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.net.URISyntaxException;

@ControllerAdvice
public class EmployeeNotFoundAdvice {

    private static final Logger log = LoggerFactory.getLogger(EmployeeNotFoundAdvice.class);

    // TODO: Inject higher level object instead of HttpServletRequest?
    @ExceptionHandler(EmployeeNotFoundException.class)
    ResponseEntity<ProblemDetail> employeeNotFoundHandler(HttpServletRequest sr, EmployeeNotFoundException ex) {
        sr.getAttributeNames().asIterator().forEachRemaining(
                attr -> log.info("--------> attribute name: {}", attr)
        );

        return ResponseEntity.of(ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND,
                        "no information found for requested employee: "
                                + ex.getMessage())).build();
    }
}
