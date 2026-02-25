package com.canermastan.notificationgateway.entity;

import com.canermastan.notificationgateway.model.ChannelType;
import com.canermastan.notificationgateway.model.Severity;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity
public class NotificationChannel extends PanacheEntity {
    @Enumerated(EnumType.STRING)
    public ChannelType type;

    public String targetIdentifier; // Chat ID etc.
    public String channelToken;

    @Enumerated(EnumType.STRING)
    public Severity minSeverity;

    @ManyToOne
    public Project project;
}
