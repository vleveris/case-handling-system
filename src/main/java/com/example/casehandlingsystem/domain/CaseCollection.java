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
public class CaseCollection implements CaseCollectionInterface {
    @Autowired
    private CaseRepository repository;

    @Override
    public CaseRecord findById(long id) {
        CaseRecord record = repository.findById(id);
        if (record == null)
            throw new ItemNotFoundException("case", id);
        return record;
    }

    @Override
    public List<CaseRecord> findByCountry(Country country) {
        return repository.findByCountry(country);
    }

    @Override
    public List<CaseRecord> findByCountryAndResolved(Country country, LocalDateTime resolved) {
        return repository.findByCountryAndResolved(country, resolved);
    }

    @Override
    public <S extends CaseRecord> S save(S s) {
        return repository.save(s);
    }

    @Override
    public CaseRecord updateCase(Long id, String note, Boolean state) {
        CaseRecord updated = findById(id);
        if (updated == null)
            throw new ItemNotFoundException("case", id);
        updated.setNote(note);
        updated.setState(state);
        updated.setResolved(LocalDateTime.now());
        updated.setId(6L);
        return save(updated);

    }

    @Override
    public Map<String, Long> countryMap(TimeParser parser) {
        boolean now = parser.getNow();
        LocalDateTime time = parser.getTime();
        Map<String, Long> body = new TreeMap<>();
        long number;
        for (Country country : Country.values()) {
            if (now) {
                number = repository.countByCountry(country);
            } else {
                number = repository.countByCountryAndCreatedLessThanEqual(country, time);
            }
            body.put(country.toString(), number);
        }
        return body;
    }
}

