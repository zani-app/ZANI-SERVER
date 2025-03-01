package com.zani.zani.security.application.service;

import com.zani.zani.core.utility.JsonWebTokenUtil;
import com.zani.zani.security.application.dto.response.DefaultJsonWebTokenDto;
import com.zani.zani.security.application.usecase.ReissueJsonWebTokenUseCase;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.service.RefreshTokenService;
import com.zani.zani.security.repository.AccountRepository;
import com.zani.zani.security.repository.RefreshTokenRepository;
import com.zani.zani.security.repository.entity.redis.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReissueJsonWebTokenService implements ReissueJsonWebTokenUseCase {

    private final AccountRepository accountRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenService refreshTokenService;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(String refreshTokenValue) {

        // refresh Token 검증. Redis에 있는 토큰인지 확인 -> accountId 추출
        RefreshToken refreshToken = refreshTokenRepository.findByValueOrElseThrow(refreshTokenValue);
        UUID accountId = refreshToken.getAccountId();

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                account.getId(),
                account.getRole()
        );

        // Refresh Token 갱신
        refreshTokenRepository.save(refreshTokenService.createRefreshToken(account.getId(), defaultJsonWebTokenDto.getRefreshToken()));

        return defaultJsonWebTokenDto;
    }
}
