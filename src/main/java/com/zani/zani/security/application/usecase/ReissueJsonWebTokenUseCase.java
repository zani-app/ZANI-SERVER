package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.response.DefaultJsonWebTokenDto;

@UseCase
public interface ReissueJsonWebTokenUseCase {

    /**
     * Refresh Token을 이용하여 새로운 JsonWebToken을 발급하는 유스케이스
     * @param refreshToken Refresh Token
     * @return DefaultJsonWebTokenDto
     */
    DefaultJsonWebTokenDto execute(String refreshToken);
}
