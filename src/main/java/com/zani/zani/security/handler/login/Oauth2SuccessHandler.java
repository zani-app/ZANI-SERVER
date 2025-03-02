package com.zani.zani.security.handler.login;

import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.core.utility.HttpServletUtil;
import com.zani.zani.security.application.dto.response.OauthJsonWebTokenDto;
import com.zani.zani.security.application.usecase.LoginOauthUseCase;
import com.zani.zani.security.info.*;
import com.zani.zani.security.info.factory.Oauth2UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final LoginOauthUseCase loginOauthUseCase;
    private final HttpServletUtil httpServletUtil;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Oauth2UserInfo principal = (Oauth2UserInfo) authentication.getPrincipal();

        OauthJsonWebTokenDto oauthJsonWebTokenDto = null;
        if (principal instanceof TemporaryKakaoOauth2UserInfo) {
            oauthJsonWebTokenDto = loginOauthUseCase.execute((TemporaryKakaoOauth2UserInfo) principal);
        } else if (principal instanceof TemporaryGoogleOauth2UserInfo) {
            oauthJsonWebTokenDto = loginOauthUseCase.execute((TemporaryGoogleOauth2UserInfo) principal);
        } else if (principal instanceof KakaoOauth2UserInfo) {
            oauthJsonWebTokenDto = loginOauthUseCase.execute((KakaoOauth2UserInfo) principal);
        } else if (principal instanceof GoogleOauth2UserInfo) {
            oauthJsonWebTokenDto = loginOauthUseCase.execute((GoogleOauth2UserInfo) principal);
        } else {
            throw new CommonException(ErrorCode.INVALID_PRINCIPAL_TYPE);
        }

        httpServletUtil.onSuccessBodyResponseWithOauthJWTBody(response, oauthJsonWebTokenDto);
    }
}
