package com.hsw.birdparkmanagement.model.ui;

import com.hsw.birdparkmanagement.Exceptions.BirdparkException;
import lombok.Getter;

@Getter
public class ROError {
    private int errorCode;
    private String errorTitle;
    private String errorMessage;

    public ROError(int errorCode, String errorTitle, String errorMessage) {
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
    }

    public ROError(BirdparkException e) {
        this.errorCode = e.getErrorCode();
        this.errorTitle = e.getErrorTitle();
        this.errorMessage = e.getMessage();
    }
}
