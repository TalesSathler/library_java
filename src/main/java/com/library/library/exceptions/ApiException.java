package com.library.library.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ApiException extends Exception {

    public ApiException(String msg) {
        super(msg);
    }


    public ApiException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
