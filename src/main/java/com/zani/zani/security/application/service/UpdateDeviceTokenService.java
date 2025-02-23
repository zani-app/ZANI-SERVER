package com.zani.zani.security.application.service;

import com.zani.zani.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.zani.zani.security.application.usecase.UpdateDeviceTokenUseCase;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateDeviceTokenRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 디바이스 토큰 갱신
        Account updatedAccount = account.updateDeviceToken(requestDto.deviceToken());
        accountRepository.save(updatedAccount);
    }
}
