package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.request.SignUpOauthRequestDto;

@UseCase
public interface SignUpOauthUseCase {
    /**
     * 소셜 로그인 회원가입 유스케이스
     * @param temporaryToken 임시 토큰
     * @param requestDto 소셜 로그인 회원가입 요청 DTO
     */
    void execute(String temporaryToken, SignUpOauthRequestDto requestDto);
}
