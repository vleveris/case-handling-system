package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.models.Case;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CaseRepository extends CrudRepository<Case, Long> {
    List<Case> findAll();

    Case findById(long id);

    List<Case> findByCountry(Country country);

    List<Case> findByCountryAndResolved(Country country, LocalDateTime resolved);

    long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created);

    long countByCountry(Country country);

    <S extends Case> S save(S s);
}
