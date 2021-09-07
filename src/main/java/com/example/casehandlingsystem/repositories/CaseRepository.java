package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.domain.CaseRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CaseRepository extends CrudRepository<CaseRecord, Long> {
    List<CaseRecord> findAll();

    CaseRecord findById(long id);

    List<CaseRecord> findByCountry(Country country);

    List<CaseRecord> findByCountryAndResolved(Country country, LocalDateTime resolved);

    long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created);

    long countByCountry(Country country);

    <S extends CaseRecord> S save(S s);
}
