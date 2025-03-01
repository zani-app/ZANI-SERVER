package com.zani.zani.security.application.usecase;


import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.response.ValidationResponseDto;

@UseCase
public interface ValidateNicknameUseCase {

        /**
        * ID 유효성 검사
        * @param nickname 회원가입시 입력하는 닉네임
        * @return ValidationResponseDto
        */
        ValidationResponseDto execute(String nickname);
}
