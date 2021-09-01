package com.example.casehandlingsystem.services;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.exceptions.ItemNotFoundException;
import com.example.casehandlingsystem.models.Case;
import com.example.casehandlingsystem.repositories.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseService implements ICaseService {
    @Autowired
    private CaseRepository repository;

    @Override
    public List<Case> findAll() {
        return repository.findAll();
    }

    @Override
    public Case findById(long id) {
        Case c = repository.findById(id);
        if (c == null)
            throw new ItemNotFoundException("case", id);
        return c;
    }

    @Override
    public List<Case> findByCountry(Country country) {
        return repository.findByCountry(country);
    }

    @Override
    public List<Case> findByCountryAndResolved(Country country, LocalDateTime resolved) {
        return repository.findByCountryAndResolved(country, resolved);
    }

    @Override
    public long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created) {
        return repository.countByCountryAndCreatedLessThanEqual(country, created);
    }

    @Override
    public long countByCountry(Country country) {
        return repository.countByCountry(country);
    }

    @Override
    public <S extends Case> S save(S s) {
        return repository.save(s);
    }
}
