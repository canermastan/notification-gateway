package com.canermastan.notificationgateway.exception;

public class NotificationException extends RuntimeException {
    private final int statusCode;

    public NotificationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
