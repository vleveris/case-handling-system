package com.example.casehandlingsystem.domain;

import com.example.casehandlingsystem.constants.Currency;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class PaymentRecord implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String code;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private Currency currency;

    public PaymentRecord() {
    }

    public PaymentRecord(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRecord payment = (PaymentRecord) o;
        return Double.compare(payment.amount, amount) == 0 && Objects.equals(id, payment.id) && Objects.equals(code, payment.code) && currency == payment.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, amount, currency);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}