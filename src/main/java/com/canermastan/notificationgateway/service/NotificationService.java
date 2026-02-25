package com.canermastan.notificationgateway.service;

import com.canermastan.notificationgateway.entity.Project;
import com.canermastan.notificationgateway.exception.NotificationException;
import com.canermastan.notificationgateway.model.NotificationRequest;
import com.canermastan.notificationgateway.provider.NotificationProvider;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class NotificationService {
    @Inject
    Instance<NotificationProvider> providers;

    @WithSession
    public Uni<Void> process(NotificationRequest request) {
        return Project.findByApiKey(request.apiKey())
                .onItem().ifNull().failWith(new NotificationException("Geçersiz API Key", Response.Status.UNAUTHORIZED.getStatusCode()))
                .onItem().transformToMulti(project -> Multi.createFrom().iterable(project.channels))
                .onItem().transformToUniAndMerge(channel -> {
                    if (request.severity().ordinal() >= channel.minSeverity.ordinal()) {
                        return providers.stream()
                                .filter(p -> p.getType() == channel.type)
                                .findFirst()
                                .map(p -> p.send(channel, request.message()))
                                .orElse(Uni.createFrom().voidItem());
                    }
                    return Uni.createFrom().voidItem();
                }).collect().last().replaceWithVoid();
    }
}
