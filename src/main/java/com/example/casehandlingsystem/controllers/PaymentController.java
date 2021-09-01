package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.helpers.TimeParser;
import com.example.casehandlingsystem.models.Payment;
import com.example.casehandlingsystem.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @GetMapping("/payment/{id}")
    Payment getPayment(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @PostMapping("/payment/new")
    ResponseEntity<Payment> newPayment(@RequestBody Payment payment) {
        Payment newP = paymentService.save(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newP);
    }

    @GetMapping("/payment/unresolved/sum")
    Map<String, Double> getUnresolvedSum(@RequestParam(required = false) String year,
                                         @RequestParam(required = false) String month,
                                         @RequestParam(required = false) String day,
                                         @RequestParam(required = false) String hour,
                                         @RequestParam(required = false) String minute,
                                         @RequestParam(required = false) String second,
                                         @RequestParam(required = false) String nanosecond) {
        TimeParser parser = new TimeParser(year, month, day, hour, minute, second, nanosecond);
        boolean now = parser.getNow();
        LocalDateTime time = parser.getTime();
        double sum;
        if (now) {
            sum = paymentService.getUnresolvedSum();
        } else {
            sum = paymentService.getUnresolvedSumAndTime(time);
        }
        return Map.of("sum", sum);
    }
}