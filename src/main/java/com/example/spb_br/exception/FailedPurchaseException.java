package com.example.spb_br.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FailedPurchaseException extends RuntimeException {
    public FailedPurchaseException(String msg) {
        super(msg);
    }
}
