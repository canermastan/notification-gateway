package com.canermastan.notificationgateway.exception;

import com.canermastan.notificationgateway.model.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof NotificationException) {
            NotificationException notificationException = (NotificationException) exception;
            return Response.status(notificationException.getStatusCode())
                    .entity(new ErrorResponse(notificationException.getMessage(), null, LocalDateTime.now()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Sunucu hatası oluştu", exception.getMessage(), LocalDateTime.now()))
                .build();
    }
}