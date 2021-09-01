package com.example.casehandlingsystem.controllers;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.exceptions.ApiError;
import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.helpers.CasePost;
import com.example.casehandlingsystem.helpers.CasePut;
import com.example.casehandlingsystem.helpers.TimeParser;
import com.example.casehandlingsystem.models.Case;
import com.example.casehandlingsystem.models.Payment;
import com.example.casehandlingsystem.services.ICaseService;
import com.example.casehandlingsystem.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@RestController
public class CaseController {

    @Autowired
    private ICaseService caseService;
    @Autowired
    private IPaymentService paymentService;

    @GetMapping("/case/id/{id}")
    Case getCase(@PathVariable long id) {
        return caseService.findById(id);
    }

    @PostMapping("/case/new")
    ResponseEntity<Case> newCase(@RequestBody CasePost casePost) {
        Payment p = paymentService.findById(casePost.getPaymentId());
        if (p == null)
            throw new ItemNotFoundException("Payment", casePost.getPaymentId());
        List<Payment> exists = paymentService.findByCode(casePost.getCode());
        if (!exists.isEmpty())
            throw new ApiError(HttpStatus.BAD_REQUEST.value(), "Payment code " + casePost.getCode() + " already exists.");
        p.setCode(casePost.getCode());
        paymentService.save(p);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(caseService.save(new Case(p, casePost.getCountry())));
    }

    @GetMapping("/case/country/{country}")
    List<Case> getByCountry(@PathVariable Country country) {
        return caseService.findByCountry(country);
    }

    @GetMapping("/case/resolve/{country}")
    List<Case> getUnresolvedByCountry(@PathVariable Country country) {
        return caseService.findByCountryAndResolved(country, null);
    }

    @PutMapping("/case/resolve")
    Case resolveCase(@RequestBody CasePut casePut) {
        Case c = caseService.findById(casePut.getId());
        if (c == null)
            throw new ItemNotFoundException("case", casePut.getId());
        c.setNote(casePut.getNote());
        c.setResolved(LocalDateTime.now());
        return caseService.save(c);
    }

    @GetMapping("/case/total")
    TreeMap<String, Long> getCaseNumberPerCountry(@RequestParam(required = false) String year,
                                                  @RequestParam(required = false) String month,
                                                  @RequestParam(required = false) String day,
                                                  @RequestParam(required = false) String hour,
                                                  @RequestParam(required = false) String minute,
                                                  @RequestParam(required = false) String second,
                                                  @RequestParam(required = false) String nanosecond) {
        TimeParser parser = new TimeParser(year, month, day, hour, minute, second, nanosecond);
        boolean now = parser.getNow();
        LocalDateTime time = parser.getTime();
        TreeMap<String, Long> body = new TreeMap<>();
        long number;
        for (Country country : Country.values()) {
            if (now) {
                number = caseService.countByCountry(country);
            } else {
                number = caseService.countByCountryAndCreatedLessThanEqual(country, time);
            }
            body.put(country.toString(), number);
        }
        return body;
    }

}

