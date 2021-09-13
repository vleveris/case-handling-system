package com.example.casehandlingsystem.exceptions;

public class ApiError extends RuntimeException {
    private final int code;
    private String text;

    public ApiError(int code, String text) {
        super(text);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
