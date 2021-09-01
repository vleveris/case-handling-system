package com.example.casehandlingsystem.helpers;

import com.example.casehandlingsystem.constants.Country;


public class CasePost {
    private Long paymentId;
    private Country country;
    private String code;

    public CasePost(Long paymentId, String code, Country country) {
        this.paymentId = paymentId;
        this.code = code;
        this.country = country;
    }

    public CasePost() {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public String getCode() {
        return code;
    }

    public Country getCountry() {
        return country;
    }
}
