package com.hsw.birdparkgeneral.api.ui;
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
}
