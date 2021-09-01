package com.example.casehandlingsystem.exceptions;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends ApiError {
    public ItemNotFoundException(String what, Long id) {
        super(HttpStatus.NOT_FOUND.value(), String.format("%s with ID %s was not found.", what, id));
    }


}
