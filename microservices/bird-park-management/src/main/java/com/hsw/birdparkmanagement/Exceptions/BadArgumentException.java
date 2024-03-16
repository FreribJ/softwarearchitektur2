package com.hsw.birdparkmanagement.Exceptions;

public class BadArgumentException extends BirdparkException{
    public BadArgumentException(String errorMessage) {
        super(400, "Bad Argument", errorMessage);
    }

    public BadArgumentException() {
        super(400, "Bad Argument");
    }
}
