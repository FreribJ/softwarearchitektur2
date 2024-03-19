package com.hsw.birdparkmanagement.Exceptions;

import lombok.Getter;

@Getter
public class BirdparkException extends RuntimeException {

    private int errorCode;
    private String errorTitle;

    public BirdparkException(int errorCode, String errorTitle, String errorMessage) {
        super(errorMessage);
        this.errorTitle = errorTitle;
        this.errorCode = errorCode;
    }

    public BirdparkException(int errorCode, String errorTitle) {
        super();
        this.errorTitle = errorTitle;
        this.errorCode = errorCode;
    }
}
