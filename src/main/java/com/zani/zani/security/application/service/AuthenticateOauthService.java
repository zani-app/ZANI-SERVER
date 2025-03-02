package com.zani.zani.security.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.core.utility.RestClientUtil;
import com.zani.zani.security.application.dto.request.OauthLoginRequestDto;
import com.zani.zani.security.application.usecase.AuthenticateOauthUseCase;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.info.GoogleOauth2UserInfo;
import com.zani.zani.security.info.KakaoOauth2UserInfo;
import com.zani.zani.security.info.TemporaryKakaoOauth2UserInfo;
import com.zani.zani.security.info.factory.Oauth2UserInfo;
import com.zani.zani.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.zani.zani.security.domain.type.ESecurityProvider.KAKAO;

@Service
@RequiredArgsConstructor
public class AuthenticateOauthService implements AuthenticateOauthUseCase {
    @Value("${oauth.kakao.url}")
    private String kakaoUrl;

    @Value("${oauth.kakao.path}")
    private String kakaoPath;

    @Value("${oauth.google.url}")
    private String googleUrl;

    @Value("${oauth.google.path}")
    private String googlePath;

    private final AccountRepository accountRepository;

    private final RestClientUtil restClientUtil;
    private final ObjectMapper objectMapper;

    @Override
    public Oauth2UserInfo execute(OauthLoginRequestDto requestDto) {
        switch (ESecurityProvider.fromString(requestDto.provider())) {
            case KAKAO:
                try {
                    // Kakao Oauth Provider 로부터 사용자 정보 제공받음
                    JSONObject jsonObject = restClientUtil.sendGetMethodWithAuthorizationHeader(
                            kakaoUrl + kakaoPath, requestDto.accessToken()
                    );
                    Oauth2UserInfo principal = objectMapper.readValue(jsonObject.toString(), KakaoOauth2UserInfo.class);

                    if (accountRepository.findBySerialIdAndProviderOrElseNull(principal.getId(), KAKAO) == null) {
                        return objectMapper.readValue(jsonObject.toString(), TemporaryKakaoOauth2UserInfo.class);
                    } else {
                        return principal;
                    }

                } catch (IOException e) {
                    throw new RuntimeException("Failed to authenticate with Kakao : {}", e);
                }
            case GOOGLE:
                try {
                    // Google Oauth Provider 로부터 사용자 정보 제공받음
                    JSONObject jsonObject = restClientUtil.sendGetMethodWithAuthorizationHeader(
                            googleUrl + googlePath, requestDto.accessToken()
                    );
                    Oauth2UserInfo principal = objectMapper.readValue(jsonObject.toString(), GoogleOauth2UserInfo.class);

                    if (accountRepository.findBySerialIdAndProviderOrElseNull(principal.getId(), ESecurityProvider.GOOGLE) == null) {
                        return objectMapper.readValue(jsonObject.toString(), TemporaryKakaoOauth2UserInfo.class);
                    } else {
                        return principal;
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to authenticate with Google : {}", e);
                }
            default:
                throw new CommonException(ErrorCode.INVALID_ENUM_TYPE);
        }
    }
}

