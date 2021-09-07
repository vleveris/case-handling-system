package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.domain.PaymentRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentRecord, Long> {
    PaymentRecord findById(long id);

    List<PaymentRecord> findByCode(String code);

    <S extends PaymentRecord> S save(S s);

    @Query(value = "select sum(amount) from payment,  casep where payment.id=casep.payment_id and state=false", nativeQuery = true)
    Double getUnresolvedSum();

    @Query(value = "select sum(amount) from payment,  casep where payment.id=casep.payment_id and created<=:time and(resolved is null or resolved>:time)", nativeQuery = true)
    Double getUnresolvedSumAndTime(LocalDateTime time);

}
