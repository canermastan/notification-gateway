package com.canermastan.notificationgateway.model;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String details,
        LocalDateTime timestamp
) { }
