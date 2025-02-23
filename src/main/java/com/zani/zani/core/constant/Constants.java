package com.zani.zani.core.constant;

import java.util.List;

public class Constants {

    // JWT
    public static String ACCOUNT_ID_ATTRIBUTE_NAME = "ACCOUNT_ID";
    public static String ACCOUNT_ID_CLAIM_NAME = "aid";
    public static String ACCOUNT_ROLE_CLAIM_NAME = "rol";

    // HEADER
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    
    /**
     * 인증이 필요 없는 URL
     */
    public static List<String> NO_NEED_AUTH_URLS = List.of(
            // Authentication/Authorization
            "/oauth2/authorization/kakao",
            "/login/oauth2/code/kakao",
            "/oauth2/authorization/google",
            "/login/oauth2/code/google",
            "/v1/auth/login",
            "/v1/auth/reissue/token",

            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**"
    );
}
