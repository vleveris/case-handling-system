package com.example.casehandlingsystem.models;

import com.example.casehandlingsystem.constants.Country;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "casep")

public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;
    @OneToOne
    private Payment payment;
    @Column(nullable = false)
    private Country country;
    @Column
    private String note;
    @Column(nullable = false)
    private LocalDateTime created;
    @Basic
    private LocalDateTime resolved;
    @Basic
    private boolean state;

    public Case() {
    }

    public Case(Payment payment, Country country) {
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return state == aCase.state && Objects.equals(id, aCase.id) && Objects.equals(payment, aCase.payment) && country == aCase.country && Objects.equals(note, aCase.note) && Objects.equals(resolved, aCase.resolved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, country, note, created, resolved, state);
    }
}


