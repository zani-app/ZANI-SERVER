package com.zani.zani.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpOauthRequestDto(

        @JsonProperty("nickname")
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname
) {
}
