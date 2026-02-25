package com.canermastan.notificationgateway.resource;

import com.canermastan.notificationgateway.entity.NotificationChannel;
import com.canermastan.notificationgateway.model.ChannelType;
import com.canermastan.notificationgateway.model.Severity;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.canermastan.notificationgateway.entity.Project;
import io.quarkus.hibernate.reactive.panache.common.WithSession;

import java.util.UUID;

@Path("/admin")
public class AdminResource {

    @Inject
    Template admin;

    @Inject
    Template success;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @WithSession
    public Uni<TemplateInstance> list() {
        return Project.listAll().onItem().transform(projects ->
                admin.data("projects", projects)
                        .data("channelTypes", ChannelType.values()) // Enum listesini gönderiyoruz
        );
    }

    @POST
    @Path("/projects")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @WithTransaction
    public Uni<TemplateInstance> create(
            @FormParam("name") String name,
            @FormParam("type") ChannelType type,
            @FormParam("token") String token,
            @FormParam("targetId") String targetId) {

        String newApiKey = UUID.randomUUID().toString();

        Project project = new Project();
        project.name = name;
        project.apiKey = newApiKey;
        project.isActive = true;

        NotificationChannel channel = new NotificationChannel();
        channel.type = type;
        channel.channelToken = token;
        channel.targetIdentifier = targetId;
        channel.minSeverity = Severity.INFO;
        channel.project = project;

        return project.persist()
                .chain(channel::persist)
                .replaceWith(() -> success.data("apiKey", newApiKey));
    }
}