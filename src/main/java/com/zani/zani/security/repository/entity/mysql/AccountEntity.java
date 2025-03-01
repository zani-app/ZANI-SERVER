package com.zani.zani.security.repository.entity.mysql;

import com.zani.zani.account.domain.User;
import com.zani.zani.account.repository.entity.UserEntity;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.type.ESecurityProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_accounts_serial_id",
                        columnNames = {"serial_id"}
                )
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@DynamicUpdate
public abstract class AccountEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /* -------------------------------------------- */
    /* Security Column ---------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    private ESecurityProvider provider;

    @Column(name = "serial_id", length = 20, nullable = false, updatable = false)
    private String serialId;

    @Column(name = "password", length = 320, nullable = false)
    private String password;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "device_token", length = 320)
    private String deviceToken;

    @Column(name = "notification_config")
    private Boolean notificationConfig;

    /* -------------------------------------------- */
    /* TimeStamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    public AccountEntity(
            UUID id,
            ESecurityProvider provider,
            String serialId,
            String password,
            String deviceToken,
            Boolean notificationConfig
    ) {
        this.id = id;
        this.provider = provider;
        this.serialId = serialId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.deviceToken = deviceToken;
        this.notificationConfig = notificationConfig;
    }

    public Account toAccount() {
        if (this instanceof UserEntity userEntity) {
            return User.builder()
                    .id(this.id)
                    .provider(this.provider)
                    .serialId(this.serialId)
                    .password(this.password)
                    .deviceToken(this.deviceToken)
                    .notificationConfig(this.notificationConfig)
                    .createdAt(this.createdAt)
                    .nickname(userEntity.getNickname())
                    .profileImgUrl(userEntity.getProfileImgUrl())
                    .build();
        } else {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }
}
