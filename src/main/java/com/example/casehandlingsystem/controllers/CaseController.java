package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.domain.CaseCollectionInterface;
import com.example.casehandlingsystem.domain.CaseRecord;
import com.example.casehandlingsystem.domain.PaymentCollectionInterface;
import com.example.casehandlingsystem.domain.PaymentRecord;
import com.example.casehandlingsystem.exceptions.ApiError;
import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.helpers.CasePost;
import com.example.casehandlingsystem.helpers.CasePut;
import com.example.casehandlingsystem.helpers.TimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CaseController {

    @Autowired
    private PaymentCollectionInterface paymentCollection;
    @Autowired
    private CaseCollectionInterface caseCollection;

    @GetMapping("/case/id/{id}")
    CaseRecord getCase(@PathVariable long id) {
        return caseCollection.findById(id);
    }

    @PostMapping("/case/new")
    ResponseEntity<CaseRecord> newCase(@RequestBody CasePost casePost) {
        PaymentRecord newPayment = paymentCollection.findById(casePost.getPaymentId());
        if (newPayment == null)
            throw new ItemNotFoundException("Payment", casePost.getPaymentId());
        List<PaymentRecord> exists = paymentCollection.findByCode(casePost.getCode());
        if (!exists.isEmpty())
            throw new ApiError(HttpStatus.BAD_REQUEST.value(), "Payment code " + casePost.getCode() + " already exists.");
        newPayment.setCode(casePost.getCode());
        paymentCollection.save(newPayment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(caseCollection.save(new CaseRecord(newPayment, casePost.getCountry())));
    }

    @GetMapping("/case/country/{country}")
    List<CaseRecord> getByCountry(@PathVariable Country country) {
        return caseCollection.findByCountry(country);
    }

    @GetMapping("/case/resolve/{country}")
    List<CaseRecord> getUnresolvedByCountry(@PathVariable Country country) {
        return caseCollection.findByCountryAndResolved(country, null);
    }

    @PutMapping("/case/resolve")
    CaseRecord resolveCase(@RequestBody CasePut casePut) {
        return caseCollection.updateCase(casePut.getId(), casePut.getNote(), casePut.getState());
    }

    @GetMapping("/case/total")
    Map<String, Long> getCaseNumberPerCountry(@RequestParam(required = false) String year,
                                              @RequestParam(required = false) String month,
                                              @RequestParam(required = false) String day,
                                              @RequestParam(required = false) String hour,
                                              @RequestParam(required = false) String minute,
                                              @RequestParam(required = false) String second,
                                              @RequestParam(required = false) String nanosecond) {
        TimeParser parser = new TimeParser(year, month, day, hour, minute, second, nanosecond);
        return caseCollection.countryMap(parser);
    }

}

