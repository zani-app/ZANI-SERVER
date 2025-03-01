package com.zani.zani.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.security.application.dto.request.OauthLoginRequestDto;
import com.zani.zani.security.application.usecase.AuthenticateOauthUseCase;
import com.zani.zani.security.handler.login.Oauth2SuccessHandler;
import com.zani.zani.security.info.factory.Oauth2UserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class OauthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final Oauth2SuccessHandler oauthLoginSuccessHandler;
    private final AuthenticateOauthUseCase authenticateOauthUseCase;

    public OauthAuthenticationFilter(
            String defaultFilterProcessesUrl,
            Oauth2SuccessHandler oauthLoginSuccessHandler,
            AuthenticateOauthUseCase authenticateOauthUseCase
            ) {
        super(defaultFilterProcessesUrl);
        this.oauthLoginSuccessHandler = oauthLoginSuccessHandler;
        this.authenticateOauthUseCase = authenticateOauthUseCase;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            OauthLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), OauthLoginRequestDto.class);

            Oauth2UserInfo principal = authenticateOauthUseCase.execute(requestDto);

            return new UsernamePasswordAuthenticationToken(principal, null, null);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        oauthLoginSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }
}
