package com.example.casehandlingsystem.helpers;

public class CasePut {
    private Long id;


    private Boolean state;
    private String note;

    public CasePut() {
    }

    public CasePut(Long id, Boolean state, String note) {
        this.id = id;
        this.state = state;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
