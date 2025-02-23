package com.zani.zani.account.repository.entity;

import com.zani.zani.account.domain.User;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.repository.entity.mysql.AccountEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_user_account")
)
@DiscriminatorValue("USER")
@OnDelete(action = OnDeleteAction.CASCADE)
@DynamicUpdate
public class UserEntity extends AccountEntity {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Column(name = "profile_img_url", length = 500)
    private String profileImgUrl;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserEntity(
            UUID id,
            ESecurityProvider provider,
            String serialId,
            String password,
            String deviceToken,
            Boolean notificationConfig,
            String nickname,
            String profileImgUrl
    ) {
        super(id, provider, serialId, password, deviceToken, notificationConfig);
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }


    public User toUser() {
        return User.builder()
                .id(getId())
                .provider(getProvider())
                .serialId(getSerialId())
                .password(getPassword())
                .deviceToken(getDeviceToken())
                .notificationConfig(getNotificationConfig())
                .createdAt(getCreatedAt())
                .nickname(getNickname())
                .profileImgUrl(getProfileImgUrl())
                .build();
    }
}

