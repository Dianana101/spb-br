package com.example.spb_br.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PerformActionsException extends RuntimeException {
    public PerformActionsException(String msg) {
        super(msg);
    }
}
