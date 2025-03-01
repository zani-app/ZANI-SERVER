package com.zani.zani.security.application.service;

import com.zani.zani.core.utility.JsonWebTokenUtil;
import com.zani.zani.security.application.dto.response.OauthJsonWebTokenDto;
import com.zani.zani.security.application.usecase.LoginOauthUseCase;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.service.RefreshTokenService;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.info.*;
import com.zani.zani.security.info.factory.Oauth2UserInfo;
import com.zani.zani.security.repository.AccountRepository;
import com.zani.zani.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginOauthService implements LoginOauthUseCase {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenService refreshTokenService;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    public OauthJsonWebTokenDto execute(TemporaryKakaoOauth2UserInfo principal) {

        return jsonWebTokenUtil.generateOauthJsonWebTokens(
                principal.getId() + ":" + ESecurityProvider.KAKAO
        );
    }

    @Override
    public OauthJsonWebTokenDto execute(TemporaryGoogleOauth2UserInfo principal) {

        return jsonWebTokenUtil.generateOauthJsonWebTokens(
                principal.getId() + ":" + ESecurityProvider.GOOGLE
        );
    }


    @Override
    public OauthJsonWebTokenDto execute(KakaoOauth2UserInfo principal) {
        // 임시유저가 아니라면 Account 조회
        Account account = accountRepository.findBySerialIdAndProviderOrElseThrow(principal.getId(), ESecurityProvider.KAKAO);

        // Account 정보를 이용하여 Oauth Json Web Token 생성
        OauthJsonWebTokenDto jsonWebTokenDto = jsonWebTokenUtil.generateOauthJsonWebTokens(
                account.getId(),
                account.getRole()
        );

        String refreshToken = jsonWebTokenDto.getRefreshToken();

        // Refresh Token Redis 에 저장
        if (refreshToken != null) {
            refreshTokenRepository.save(refreshTokenService.createRefreshToken(account.getId(), refreshToken));
        }

        return jsonWebTokenDto;
    }
    @Override
    public OauthJsonWebTokenDto execute(GoogleOauth2UserInfo principal) {
        // 임시유저가 아니라면 Account 조회
        Account account = accountRepository.findBySerialIdAndProviderOrElseThrow(principal.getId(), ESecurityProvider.GOOGLE);

        // Account 정보를 이용하여 Oauth Json Web Token 생성
        OauthJsonWebTokenDto jsonWebTokenDto = jsonWebTokenUtil.generateOauthJsonWebTokens(
                account.getId(),
                account.getRole()
        );

        String refreshToken = jsonWebTokenDto.getRefreshToken();

        // Refresh Token Redis 에 저장
        if (refreshToken != null) {
            refreshTokenRepository.save(refreshTokenService.createRefreshToken(account.getId(), refreshToken));
        }

        return jsonWebTokenDto;
    }
}
