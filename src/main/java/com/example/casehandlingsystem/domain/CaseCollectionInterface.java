package com.example.casehandlingsystem.domain;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.helpers.TimeParser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public interface CaseCollectionInterface {
    CaseRecord findById(long id);

    List<CaseRecord> findByCountry(Country country);

    List<CaseRecord> findByCountryAndResolved(Country country, LocalDateTime resolved);

    <S extends CaseRecord> S save(S s);

    CaseRecord updateCase(Long id, String note, Boolean state);

    Map<String, Long> countryMap(TimeParser parser);

}
