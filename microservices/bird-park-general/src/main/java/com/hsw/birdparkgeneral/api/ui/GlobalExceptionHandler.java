package com.hsw.birdparkgeneral.api.ui;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ApiResponse(responseCode = "503", description = "Connection Error")
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ROError> handleConnectException(ConnectException e) {
        return ResponseEntity.status(500).body(new ROError(500, "Connection Error", "Could not connect to the backend service"));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "503", description = "Connection Error")
    public ResponseEntity<ROError> handleBirdparkException(HttpClientErrorException e) {
        HttpStatusCode statusCode = e.getStatusCode();
        ROError responseBody = e.getResponseBodyAs(ROError.class);
        return ResponseEntity.status(statusCode).body(responseBody);
    }


    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ROError> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(new ROError(500, "Unexpected Error", e.getMessage()));
    }
}
