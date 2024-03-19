package com.hsw.birdparkmanagement.Exceptions;

public class UnexpectedErrorException extends BirdparkException {
    public UnexpectedErrorException(String errorMessage) {
        super(500, "Unexpected Error", errorMessage);
    }

    public UnexpectedErrorException() {
        super(500, "Unexpected Error");
    }
}
