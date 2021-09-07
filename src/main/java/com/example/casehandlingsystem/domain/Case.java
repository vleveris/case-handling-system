package com.example.casehandlingsystem.domain;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.helpers.TimeParser;
import com.example.casehandlingsystem.repositories.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class Case {
    @Autowired
    private CaseRepository repository;

    private List<CaseRecord> findAll() {
        return repository.findAll();
    }

    public CaseRecord findById(long id) {
        CaseRecord c = repository.findById(id);
        if (c == null)
            throw new ItemNotFoundException("case", id);
        return c;
    }

    public List<CaseRecord> findByCountry(Country country) {
        return repository.findByCountry(country);
    }

    public List<CaseRecord> findByCountryAndResolved(Country country, LocalDateTime resolved) {
        return repository.findByCountryAndResolved(country, resolved);
    }

    private long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created) {
        return repository.countByCountryAndCreatedLessThanEqual(country, created);
    }

    private long countByCountry(Country country) {
        return repository.countByCountry(country);
    }

    public <S extends CaseRecord> S save(S s) {
        return repository.save(s);
    }

    public CaseRecord updateCase(Long id, String note, Boolean state) {
        CaseRecord updated = findById(id);
        if (updated == null)
            throw new ItemNotFoundException("case", id);
        updated.setNote(note);
        updated.setState(state);
        updated.setResolved(LocalDateTime.now());
        return save(updated);

    }

    public Map<String, Long> countryMap(TimeParser parser) {
        boolean now = parser.getNow();
        LocalDateTime time = parser.getTime();
        Map<String, Long> body = new TreeMap<>();
        long number;
        for (Country country : Country.values()) {
            if (now) {
                number = countByCountry(country);
            } else {
                number = countByCountryAndCreatedLessThanEqual(country, time);
            }
            body.put(country.toString(), number);
        }
        return body;

    }

}


