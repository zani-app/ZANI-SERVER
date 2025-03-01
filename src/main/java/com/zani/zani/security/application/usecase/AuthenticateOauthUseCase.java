package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.request.OauthLoginRequestDto;
import com.zani.zani.security.info.factory.Oauth2UserInfo;

@UseCase
public interface AuthenticateOauthUseCase {
    /**
     * Security에서 Oauth 로그인 시 사용되는, Oauth 토큰을 바탕으로 Oauth Provider 로부터 유저 정보를 제공받는 유스케이스.
     * @param requestDto OauthLoginRequestDto
     * @return Oauth2UserInfo
     */
    Oauth2UserInfo execute(OauthLoginRequestDto requestDto);
}
