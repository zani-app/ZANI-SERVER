package com.zani.zani.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zani.zani.core.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public sealed class ExceptionDto permits ArgumentNotValidExceptionDto {

    @JsonProperty("code")
    private final Integer code;

    @JsonProperty("message")
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExceptionDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode.getCode(), errorCode.getMessage());
    }

    public static ExceptionDto of(ErrorCode errorCode, String message) {
        return new ExceptionDto(errorCode.getCode(), message);
    }
}
