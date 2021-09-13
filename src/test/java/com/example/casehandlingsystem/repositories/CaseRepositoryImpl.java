package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.constants.Country;
import com.example.casehandlingsystem.domain.CaseRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CaseRepositoryImpl implements CaseRepository {
    private final List<CaseRecord> cases = new ArrayList<>();

    @Override
    public <S extends CaseRecord> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<CaseRecord> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<CaseRecord> findAll() {
        return null;
    }

    @Override
    public Iterable<CaseRecord> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return cases.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(CaseRecord caseRecord) {

    }

    @Override
    public void deleteAll(Iterable<? extends CaseRecord> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public CaseRecord findById(long id) {
        for (CaseRecord c : cases) {
            if (c.getId().equals(id))
                try {
                    return (CaseRecord) c.clone();
                } catch (CloneNotSupportedException e) {
                    return c;
                }
        }
        return null;

    }

    @Override
    public List<CaseRecord> findByCountry(Country country) {

        List<CaseRecord> found = new ArrayList<>();
        for (CaseRecord c : cases) {
            if (country == c.getCountry())
                try {
                    found.add((CaseRecord) c.clone());

                } catch (CloneNotSupportedException e) {
                    found.add(c);
                }
        }
        return found;
    }

    @Override
    public List<CaseRecord> findByCountryAndResolved(Country country, LocalDateTime resolved) {
        List<CaseRecord> found = new ArrayList<>();
        for (CaseRecord c : cases) {
            if (c.getCountry() == country && c.getResolved() == resolved)

                try {
                    found.add((CaseRecord) c.clone());
                } catch (CloneNotSupportedException e) {
                    found.add(c);
                }
        }
        return found;
    }

    @Override
    public long countByCountryAndCreatedLessThanEqual(Country country, LocalDateTime created) {
        return 0;
    }

    @Override
    public long countByCountry(Country country) {
        return 0;
    }

    @Override
    public <S extends CaseRecord> S save(S s) {
        if (s.getId() == null) {
            long i = 1;
            while (findById(i) != null)
                i++;
            s.setId(i);

        }
        CaseRecord other = findById(s.getId().longValue());
        if (other == null)
            cases.add(s);
        else if (other.equals(s))
            throw new IllegalArgumentException();
        else {
            cases.remove(other);
            cases.add(s);
        }

        return s;

    }
}
