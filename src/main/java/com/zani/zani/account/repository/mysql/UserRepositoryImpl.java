package com.zani.zani.account.repository.mysql;

import com.zani.zani.account.domain.User;
import com.zani.zani.account.repository.UserRepository;
import com.zani.zani.account.repository.entity.UserEntity;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByIdOrElseThrow(UUID userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT))
                .toUser();
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserEntity.builder()
                .serialId(user.getSerialId())
                .provider(user.getProvider())
                .password(user.getPassword())
                .deviceToken(user.getDeviceToken())
                .notificationConfig(user.getNotificationConfig())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgUrl())
                .build();

        userJpaRepository.save(entity);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }
}
