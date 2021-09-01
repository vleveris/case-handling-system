package com.example.casehandlingsystem.services;

import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.models.Payment;
import com.example.casehandlingsystem.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository repository;

    @Override
    public Payment findById(long id) {
        Payment payment = repository.findById(id);
        if (payment == null)
            throw new ItemNotFoundException("payment", id);
        return payment;
    }

    @Override
    public List<Payment> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public <S extends Payment> S save(S s) {
        return repository.save(s);
    }

    @Override
    public double getUnresolvedSum() {
        try {
            return repository.getUnresolvedSum();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public double getUnresolvedSumAndTime(LocalDateTime time) {

        try {
            return repository.getUnresolvedSumAndTime(time);
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
