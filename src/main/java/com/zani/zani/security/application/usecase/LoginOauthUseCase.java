package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.response.OauthJsonWebTokenDto;
import com.zani.zani.security.info.GoogleOauth2UserInfo;
import com.zani.zani.security.info.KakaoOauth2UserInfo;
import com.zani.zani.security.info.TemporaryGoogleOauth2UserInfo;
import com.zani.zani.security.info.TemporaryKakaoOauth2UserInfo;
import com.zani.zani.security.info.factory.Oauth2UserInfo;

@UseCase
public interface LoginOauthUseCase {
    /**
     * Security에서 사용되는 Social Login 유스케이스
     * @param principal TemporaryKakaoOauth2UserInfo
     */

    OauthJsonWebTokenDto execute(TemporaryKakaoOauth2UserInfo principal);

    /**
     * Security에서 사용되는 Social Login 유스케이스
     * @param principal TemporaryGoogleOauth2UserInfo
     */
    OauthJsonWebTokenDto execute(TemporaryGoogleOauth2UserInfo principal);


    /**
     * Security에서 사용되는 Social Login 유스케이스
     * @param principal KakaoOauth2UserInfo
     */

    OauthJsonWebTokenDto execute(KakaoOauth2UserInfo principal);

    /**
     * Security에서 사용되는 Social Login 유스케이스
     * @param principal GoogleOauth2UserInfo
     */
    OauthJsonWebTokenDto execute(GoogleOauth2UserInfo principal);

}
