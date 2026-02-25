package com.canermastan.notificationgateway.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Project extends PanacheEntity {
    public String name;
    public String apiKey;
    public boolean isActive = true;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<NotificationChannel> channels;

    public static io.smallrye.mutiny.Uni<Project> findByApiKey(String apiKey) {
        return find("apiKey", apiKey).firstResult();
    }
}
