package com.canermastan.notificationgateway.provider;

import com.canermastan.notificationgateway.client.TelegramClient;
import com.canermastan.notificationgateway.entity.NotificationChannel;
import com.canermastan.notificationgateway.model.ChannelType;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class TelegramProvider implements NotificationProvider {
    @Inject
    @RestClient
    TelegramClient telegramClient;

    private static final Logger LOGGER = Logger.getLogger(TelegramProvider.class);

    @Override
    public ChannelType getType() {
        return ChannelType.TELEGRAM;
    }

    @Override
    public Uni<Void> send(NotificationChannel channel, String message) {
        return telegramClient.sendMessage(channel.channelToken, channel.targetIdentifier, message)
                .replaceWithVoid()
                .onFailure().recoverWithItem(err -> {
                    LOGGER.error("Telegram hatası: " + err.getMessage());
                    return null;
                });
    }
}
