package com.example.casehandlingsystem.domain;

import com.example.casehandlingsystem.constants.Country;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity

@Table(name = "casep")

public class CaseRecord implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;
    @OneToOne
    private PaymentRecord payment;
    @Column(nullable = false)
    private Country country;
    @Column
    private String note;
    @Column(nullable = false)
    private LocalDateTime created;
    @Basic
    private LocalDateTime resolved;
    @Basic
    private Boolean state;

    public CaseRecord() {
    }

    public CaseRecord(PaymentRecord payment, Country country) {
        this.payment = payment;
        this.country = country;
        this.created = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public PaymentRecord getPayment() {
        return payment;
    }

    public void setPayment(PaymentRecord payment) {
        this.payment = payment;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getResolved() {
        return resolved;
    }

    public void setResolved(LocalDateTime resolved) {
        this.resolved = resolved;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseRecord aCase = (CaseRecord) o;
        return state == aCase.state && Objects.equals(id, aCase.id) && Objects.equals(payment, aCase.payment) && country == aCase.country && Objects.equals(note, aCase.note) && Objects.equals(resolved, aCase.resolved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, country, note, created, resolved, state);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        PaymentRecord paymentRecord = new PaymentRecord(payment.getAmount(), payment.getCurrency());
        paymentRecord.setId(payment.getId());
        CaseRecord caseRecord = (CaseRecord) super.clone();
        caseRecord.setPayment(paymentRecord);
        caseRecord.setId(getId());
        caseRecord.setNote(note);
        caseRecord.setState(state);
        caseRecord.setResolved(null);
        return caseRecord;
    }
}


