package com.zani.zani.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zani.zani.security.application.dto.response.DefaultJsonWebTokenDto;
import com.zani.zani.security.application.dto.response.OauthJsonWebTokenDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HttpServletUtil {

    @Value("${json-web-token.refresh-token-expire-period}")
    private Long refreshTokenExpirePeriod;

    private final ObjectMapper objectMapper;


    public void onSuccessBodyResponseWithJWTBody(
            HttpServletResponse response,
            DefaultJsonWebTokenDto tokenDto
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("data", Map.of(
                        "access_token", tokenDto.getAccessToken(),
                        "refresh_token", tokenDto.getRefreshToken()
                )
        );
        result.put("error", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    public void onSuccessBodyResponseWithOauthJWTBody(
            HttpServletResponse response,
            OauthJsonWebTokenDto tokenDto
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.CREATED.value());

        Map<String, Object> data = new HashMap<>();
        data.put("temporary_token", tokenDto.getTemporaryToken());
        data.put("access_token", tokenDto.getAccessToken());
        data.put("refresh_token", tokenDto.getRefreshToken());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", data);
        result.put("error", null);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    public void onSuccessBodyResponse(
            HttpServletResponse response,
            HttpStatus httpStatus
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());

        Map<String, Object> result = new HashMap<>();

        if (httpStatus != HttpStatus.NO_CONTENT) {
            result.put("success", true);
            result.put("data", null);
            result.put("error", null);

            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }
}
