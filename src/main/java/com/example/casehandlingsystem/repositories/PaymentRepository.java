package com.example.casehandlingsystem.repositories;

import com.example.casehandlingsystem.models.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Payment findById(long id);

    List<Payment> findByCode(String code);

    <S extends Payment> S save(S s);

    @Query(value = "select sum(amount) from payment,  casep where payment.id=casep.payment_id and state=false", nativeQuery = true)
    Double getUnresolvedSum();

    @Query(value = "select sum(amount) from payment,  casep where payment.id=casep.payment_id and created<=:time and(resolved is null or resolved>:time)", nativeQuery = true)
    Double getUnresolvedSumAndTime(LocalDateTime time);

}
