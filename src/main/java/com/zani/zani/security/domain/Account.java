package com.zani.zani.security.domain;

import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Account {
    private final UUID id;
    private final ESecurityProvider provider;
    private final String serialId;
    private final String password;
    private final String deviceToken;
    private final Boolean notificationConfig;
    private final LocalDateTime createdAt;

    public Account(UUID id, ESecurityProvider provider, String serialId, String password, String deviceToken, Boolean notificationConfig, LocalDateTime createdAt) {
        this.id = id;
        this.provider = provider;
        this.serialId = serialId;
        this.password = password;
        this.deviceToken = deviceToken;
        this.notificationConfig = notificationConfig;
        this.createdAt = createdAt;
    }

    public abstract ESecurityRole getRole();

    public abstract String getName();

    public abstract Account updateDeviceToken(String deviceToken);
}
