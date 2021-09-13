package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.domain.CaseRecord;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
public interface CaseRepository extends CrudRepository<CaseRecord, Long> {
    CaseRecord findById(long id);

    List<CaseRecord> findByCountry(Country country);

    List<CaseRecord> findByCountryAndResolved(Country country, LocalDateTime resolved);

    long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created);

    long countByCountry(Country country);

    <S extends CaseRecord> S save(S s);
}
