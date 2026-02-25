package com.canermastan.notificationgateway.model;

public record NotificationRequest(
    String apiKey,
    String message,
    Severity severity
) {}
