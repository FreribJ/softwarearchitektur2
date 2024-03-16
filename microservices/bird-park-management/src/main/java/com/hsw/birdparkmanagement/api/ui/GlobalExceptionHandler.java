package com.hsw.birdparkmanagement.api.ui;

import com.hsw.birdparkmanagement.Exceptions.BirdparkException;
import com.hsw.birdparkmanagement.model.ui.ROError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "503", description = "Connection Error")
    @ExceptionHandler(BirdparkException.class)
    public ResponseEntity<ROError> handleBirdparkException(BirdparkException e) {
        return ResponseEntity.status(e.getErrorCode()).body(new ROError(e));
    }


    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ROError> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(new ROError(500, "Unexpected Error", e.getMessage()));
    }
}
