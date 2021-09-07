package com.example.casehandlingsystem.domain;

import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.helpers.TimeParser;
import com.example.casehandlingsystem.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class Payment {
    @Autowired
    private PaymentRepository repository;

    public PaymentRecord findById(long id) {
        PaymentRecord payment = repository.findById(id);
        if (payment == null)
            throw new ItemNotFoundException("payment", id);
        return payment;
    }

    public List<PaymentRecord> findByCode(String code) {
        return repository.findByCode(code);
    }

    public <S extends PaymentRecord> S save(S s) {
        return repository.save(s);
    }

    private double getUnresolvedSum() {
        try {
            return repository.getUnresolvedSum();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    private double getUnresolvedSumAndTime(LocalDateTime time) {

        try {
            return repository.getUnresolvedSumAndTime(time);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public Map<String, Double> getUnresolvedTotal(TimeParser parser) {
        boolean now = parser.getNow();
        LocalDateTime time = parser.getTime();
        double sum;
        if (now) {
            sum = getUnresolvedSum();
        } else {
            sum = getUnresolvedSumAndTime(time);
        }
        return Map.of("sum", sum);

    }
}




