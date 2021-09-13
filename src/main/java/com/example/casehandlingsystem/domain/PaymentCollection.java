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
public class PaymentCollection implements PaymentCollectionInterface {
    @Autowired
    private PaymentRepository repository;

    @Override
    public PaymentRecord findById(long id) {
        PaymentRecord payment = repository.findById(id);
        if (payment == null)
            throw new ItemNotFoundException("payment", id);
        return payment;
    }

    @Override
    public List<PaymentRecord> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public <S extends PaymentRecord> S save(S s) {
        return repository.save(s);
    }

    @Override
    public Map<String, Double> getUnresolvedSum(TimeParser parser) {
        boolean now = parser.getNow();
        LocalDateTime time = parser.getTime();
        double sum;

        try {
            if (now) {
                sum = repository.getUnresolvedSum();
            } else {
                sum = repository.getUnresolvedSumAndTime(time);
            }
        } catch (NullPointerException e) {
            sum = 0;
        }

        return Map.of("sum", sum);


    }

}