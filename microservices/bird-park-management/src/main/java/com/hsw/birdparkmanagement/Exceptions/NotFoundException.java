package com.hsw.birdparkmanagement.Exceptions;

public class NotFoundException extends BirdparkException {
    public NotFoundException(String objectType) {
        super(404, "Not found", objectType + " was not Found");
    }

    public NotFoundException() {
        super(404, "Not Found");
    }
}
