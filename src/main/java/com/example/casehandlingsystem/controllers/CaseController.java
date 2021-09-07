package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.domain.Case;
import com.example.casehandlingsystem.domain.CaseRecord;
import com.example.casehandlingsystem.domain.Payment;
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
    private Payment p;
    @Autowired
    private Case c;

    @GetMapping("/case/id/{id}")
    CaseRecord getCase(@PathVariable long id) {
        return c.findById(id);
    }

    @PostMapping("/case/new")
    ResponseEntity<CaseRecord> newCase(@RequestBody CasePost casePost) {
        PaymentRecord newPayment = p.findById(casePost.getPaymentId());
        if (newPayment == null)
            throw new ItemNotFoundException("Payment", casePost.getPaymentId());
        List<PaymentRecord> exists = p.findByCode(casePost.getCode());
        if (!exists.isEmpty())
            throw new ApiError(HttpStatus.BAD_REQUEST.value(), "Payment code " + casePost.getCode() + " already exists.");
        newPayment.setCode(casePost.getCode());
        p.save(newPayment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(c.save(new CaseRecord(newPayment, casePost.getCountry())));
    }

    @GetMapping("/case/country/{country}")
    List<CaseRecord> getByCountry(@PathVariable Country country) {
        return c.findByCountry(country);
    }

    @GetMapping("/case/resolve/{country}")
    List<CaseRecord> getUnresolvedByCountry(@PathVariable Country country) {
        return c.findByCountryAndResolved(country, null);
    }

    @PutMapping("/case/resolve")
    CaseRecord resolveCase(@RequestBody CasePut casePut) {
        return c.updateCase(casePut.getId(), casePut.getNote(), casePut.getState());
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
        return c.countryMap(parser);
    }

}

