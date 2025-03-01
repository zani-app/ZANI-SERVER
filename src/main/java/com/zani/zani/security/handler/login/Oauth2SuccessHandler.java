package com.zani.zani.security.handler.login;

import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.core.utility.HttpServletUtil;
import com.zani.zani.security.application.dto.response.OauthJsonWebTokenDto;
import com.zani.zani.security.application.usecase.LoginOauthUseCase;
import com.zani.zani.security.info.CustomTemporaryUserPrincipal;
import com.zani.zani.security.info.CustomUserPrincipal;
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

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        OauthJsonWebTokenDto oauthJsonWebTokenDto = null;

        if (principal instanceof CustomTemporaryUserPrincipal) {
            oauthJsonWebTokenDto = loginOauthUseCase.execute((CustomTemporaryUserPrincipal) principal);
        } else if (principal instanceof CustomUserPrincipal) {
            oauthJsonWebTokenDto = loginOauthUseCase.execute((CustomUserPrincipal) principal);
        }
        else {
            throw new CommonException(ErrorCode.INVALID_PRINCIPAL_TYPE);
        }

        httpServletUtil.onSuccessBodyResponseWithOauthJWTBody(response, oauthJsonWebTokenDto);
    }
}
