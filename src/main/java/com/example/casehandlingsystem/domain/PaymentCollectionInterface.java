package com.example.casehandlingsystem.domain;


import com.example.casehandlingsystem.helpers.TimeParser;

import java.util.List;
import java.util.Map;

public interface PaymentCollectionInterface {
    PaymentRecord findById(long id);

    List<PaymentRecord> findByCode(String code);

    <S extends PaymentRecord> S save(S s);

    Map<String, Double> getUnresolvedSum(TimeParser parser);


}
