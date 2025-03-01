package com.zani.zani.security.controller;

import com.zani.zani.core.annotation.security.AccountID;
import com.zani.zani.core.constant.Constants;
import com.zani.zani.core.dto.ResponseDto;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.core.utility.HeaderUtil;
import com.zani.zani.security.application.dto.request.SignUpOauthRequestDto;
import com.zani.zani.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.zani.zani.security.application.dto.response.DefaultJsonWebTokenDto;
import com.zani.zani.security.application.dto.response.ValidationResponseDto;
import com.zani.zani.security.application.usecase.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Hidden
@RequestMapping("/v1/auth")
public class AuthController {

    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;
    private final SignUpOauthUseCase signUpOauthUseCase;
    private final ValidateNicknameUseCase validateNicknameUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final UpdateDeviceTokenUseCase updateDeviceTokenUseCase;

    /**
     * 1.3 JWT 재발급
     */
    @PostMapping("/reissue/token")
    public ResponseDto<DefaultJsonWebTokenDto> reissueDefaultJsonWebToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        return ResponseDto.created(reissueJsonWebTokenUseCase.execute(refreshToken));
    }

    /**
     * 1.4 디바이스 토큰 갱신
     */
    @PatchMapping("/device-token")
    public ResponseDto<Void> updateDeviceToken(
            @RequestBody @Valid UpdateDeviceTokenRequestDto requestDto,
            @AccountID UUID accountId
    ) {
        updateDeviceTokenUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 2.1 회원가입(추가정보 입력)
     */
    @PostMapping("/sign-up")
    public ResponseDto<Void> signUpOauth(
            @Valid @RequestBody SignUpOauthRequestDto requestDto,
            HttpServletRequest request
    ) {
        String temporaryToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));
        signUpOauthUseCase.execute(temporaryToken, requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 2.2 닉네임 중복 확인
     */
    @GetMapping("/validation/nickname")
    public ResponseDto<ValidationResponseDto> validateNickname(
            @RequestParam String nickname
    ) {
        return ResponseDto.ok(validateNicknameUseCase.execute(nickname));
    }

    /**
     * 2.3 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseDto<Void> deleteAccount(
            @AccountID UUID accountId
    ) {
        deleteAccountUseCase.execute(accountId);
        return ResponseDto.ok(null);
    }
}
