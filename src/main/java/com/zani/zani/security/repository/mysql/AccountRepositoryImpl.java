package com.zani.zani.security.repository.mysql;

import com.zani.zani.account.domain.User;
import com.zani.zani.account.repository.entity.UserEntity;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.repository.AccountRepository;
import com.zani.zani.security.repository.entity.mysql.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account findByIdOrElseThrow(UUID id) {
        return accountJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT))
                .toAccount();
    }

    @Override
    public void save(Account account) {
        AccountEntity entity;
        if (account instanceof User) {
            entity = UserEntity.builder()
                    .id(account.getId())
                    .provider(account.getProvider())
                    .serialId(account.getSerialId())
                    .password(account.getPassword())
                    .deviceToken(account.getDeviceToken())
                    .notificationConfig(account.getNotificationConfig())
                    .nickname(((User) account).getNickname())
                    .profileImgUrl(((User) account).getProfileImgUrl())
                    .build();
        } else {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
        accountJpaRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        accountJpaRepository.deleteById(id);
    }

    @Override
    public Account findBySerialIdAndProviderOrElseThrow(String serialId, ESecurityProvider provider) {
        return accountJpaRepository.findBySerialIdAndProvider(serialId, provider)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT))
                .toAccount();
    }

    @Override
    public void existsBySerialIdAndProviderThenThrow(String serialId, ESecurityProvider provider) {
        accountJpaRepository.findBySerialIdAndProvider(serialId, provider)
                .ifPresent(account -> {
                    throw new CommonException(ErrorCode.ALREADY_EXIST_ID);
                });
    }
}
