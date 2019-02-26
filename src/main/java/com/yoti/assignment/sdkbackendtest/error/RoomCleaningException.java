package com.yoti.assignment.sdkbackendtest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoomCleaningException extends ResponseStatusException {

    public RoomCleaningException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

}
