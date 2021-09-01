package com.example.casehandlingsystem.services;

import com.example.casehandlingsystem.models.Payment;

import java.time.LocalDateTime;
import java.util.List;

public interface IPaymentService {
    Payment findById(long id);

    List<Payment> findByCode(String code);

    <S extends Payment> S save(S s);

    double getUnresolvedSum();

    double getUnresolvedSumAndTime(LocalDateTime time);

}
