package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.domain.Payment;
import com.example.casehandlingsystem.domain.PaymentRecord;
import com.example.casehandlingsystem.helpers.TimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PaymentController {
    @Autowired
    private Payment p;

    @GetMapping("/payment/{id}")
    PaymentRecord getPayment(@PathVariable Long id) {
        return p.findById(id);
    }

    @PostMapping("/payment/new")
    ResponseEntity<PaymentRecord> newPayment(@RequestBody PaymentRecord payment) {
        PaymentRecord newP = p.save(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newP);
    }

    @GetMapping("/payment/unresolved/sum")
    Map<String, Double> getUnresolvedTotal(@RequestParam(required = false) String year,
                                           @RequestParam(required = false) String month,
                                           @RequestParam(required = false) String day,
                                           @RequestParam(required = false) String hour,
                                           @RequestParam(required = false) String minute,
                                           @RequestParam(required = false) String second,
                                           @RequestParam(required = false) String nanosecond) {
        TimeParser parser = new TimeParser(year, month, day, hour, minute, second, nanosecond);
        return p.getUnresolvedTotal(parser);
    }
}