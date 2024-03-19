package com.hsw.birdparkmanagement.Exceptions;

public class ConnectionException extends BirdparkException {
    public ConnectionException(String connectionDevice) {
        super(503, "Connection Error", "Could not connect with: " + connectionDevice);
    }

    public ConnectionException() {
        super(503, "Connection Error");
    }
}
