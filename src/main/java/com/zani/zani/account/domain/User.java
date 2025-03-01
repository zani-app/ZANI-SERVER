package com.zani.zani.account.domain;

import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User extends Account {
    private final String nickname;
    private final String profileImgUrl;

    @Builder
    public User(
            UUID id,
            ESecurityProvider provider,
            String serialId,
            String password,
            String deviceToken,
            Boolean notificationConfig,
            LocalDateTime createdAt,
            String nickname,
            String profileImgUrl
    ) {
        super(id, provider, serialId, password, deviceToken, notificationConfig, createdAt);
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }

    @Override
    public String getName() {
        return nickname;
    }

    @Override
    public Account updateDeviceToken(String deviceToken) {
        return User.builder()
                .id(this.getId())
                .provider(this.getProvider())
                .serialId(this.getSerialId())
                .password(this.getPassword())
                .deviceToken(deviceToken)
                .notificationConfig(this.getNotificationConfig())
                .createdAt(this.getCreatedAt())
                .nickname(this.nickname)
                .profileImgUrl(this.profileImgUrl)
                .build();
    }
}
