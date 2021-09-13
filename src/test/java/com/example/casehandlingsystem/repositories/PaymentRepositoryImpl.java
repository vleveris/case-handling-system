package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.domain.PaymentRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final List<PaymentRecord> payments = new ArrayList<>();

    @Override
    public PaymentRecord findById(long id) {
        for (PaymentRecord p : payments) {
            if (p.getId().equals(id))
                try {
                    return (PaymentRecord) p.clone();
                } catch (CloneNotSupportedException e) {
                    return p;
                }
        }
        return null;
    }

    @Override
    public List<PaymentRecord> findByCode(String code) {
        List<PaymentRecord> found = new ArrayList<>();
        for (PaymentRecord p : payments) {
            if (code.equals(p.getCode()))
                try {
                    found.add((PaymentRecord) p.clone());
                } catch (CloneNotSupportedException e) {
                    found.add(p);
                }
        }
        return found;
    }

    @Override
    public <S extends PaymentRecord> S save(S s) {
        PaymentRecord other = findById(s.getId().longValue());
        if (other == null)
            payments.add(s);
        else if (other.equals(s))
            throw new IllegalArgumentException();
        else {
            payments.remove(other);
            payments.add(s);
        }

        return s;
    }

    @Override
    public <S extends PaymentRecord> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<PaymentRecord> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<PaymentRecord> findAll() {
        return null;
    }

    @Override
    public Iterable<PaymentRecord> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(PaymentRecord paymentRecord) {

    }

    @Override
    public void deleteAll(Iterable<? extends PaymentRecord> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Double getUnresolvedSum() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double getUnresolvedSumAndTime(LocalDateTime time) {
        throw new UnsupportedOperationException();

    }
}

