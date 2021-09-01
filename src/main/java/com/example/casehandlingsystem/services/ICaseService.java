package com.example.casehandlingsystem.services;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.models.Case;

import java.time.LocalDateTime;
import java.util.List;

public interface ICaseService {
    List<Case> findAll();

    Case findById(long id);

    List<Case> findByCountry(Country country);

    List<Case> findByCountryAndResolved(Country country, LocalDateTime resolved);

    long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created);

    long countByCountry(Country country);

    <S extends Case> S save(S s);

}
