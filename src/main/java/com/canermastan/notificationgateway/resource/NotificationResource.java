package com.canermastan.notificationgateway.resource;

import com.canermastan.notificationgateway.model.NotificationRequest;
import com.canermastan.notificationgateway.service.NotificationService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/notify")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {
    @Inject
    NotificationService notificationService;

    @POST
    public Uni<Response> sendNotification(@Valid NotificationRequest request) {
        return notificationService.process(request)
                .replaceWith(Response.ok().build());
    }
}
