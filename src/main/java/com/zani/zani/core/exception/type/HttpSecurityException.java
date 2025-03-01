package com.zani.zani.core.exception.type;

import com.zani.zani.core.exception.error.ErrorCode;
import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class HttpSecurityException extends JwtException {

    private final ErrorCode errorCode;

    public HttpSecurityException(String message, ErrorCode errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}
