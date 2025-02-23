package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.response.OauthJsonWebTokenDto;
import com.zani.zani.security.info.CustomTemporaryUserPrincipal;
import com.zani.zani.security.info.CustomUserPrincipal;

@UseCase
public interface LoginOauthUseCase {
    /**
     * Security에서 사용되는 Social Login 유스케이스
     * @param principal CustomTemporaryUserPrincipal
     */

    OauthJsonWebTokenDto execute(CustomTemporaryUserPrincipal principal);

    /**
     * Security에서 사용되는 Social Login 유스케이스
     * @param principal CustomUserPrincipal
     */

    OauthJsonWebTokenDto execute(CustomUserPrincipal principal);

}
