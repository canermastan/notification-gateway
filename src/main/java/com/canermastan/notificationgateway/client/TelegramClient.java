package com.canermastan.notificationgateway.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "telegram-api")
public interface TelegramClient {
    @POST
    @Path("/bot{token}/sendMessage")
    Uni<Object> sendMessage(
            @PathParam("token") String token,
            @QueryParam("chat_id") String chatId,
            @QueryParam("text") String text);
}