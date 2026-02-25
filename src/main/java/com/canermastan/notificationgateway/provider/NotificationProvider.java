package com.canermastan.notificationgateway.provider;

import com.canermastan.notificationgateway.entity.NotificationChannel;
import com.canermastan.notificationgateway.model.ChannelType;
import io.smallrye.mutiny.Uni;

public interface NotificationProvider {
    ChannelType getType();
    Uni<Void> send(NotificationChannel channel, String message);
}
