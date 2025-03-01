package com.zani.zani.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateDeviceTokenRequestDto(

        @JsonProperty("device_token")
        @NotBlank(message = "deviceToken은 null일 수 없습니다.")
        String deviceToken
) {
}
