package com.zani.zani.security.info.factory;

import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.info.GoogleOauth2UserInfo;
import com.zani.zani.security.info.KakaoOauth2UserInfo;

import java.util.Map;

public class Oauth2UserInfoFactory {
    public static Oauth2UserInfo getOauth2UserInfo(ESecurityProvider provider, Map<String, Object> attributes){
        return switch (provider) {
            case KAKAO -> new KakaoOauth2UserInfo(attributes);
            case GOOGLE -> new GoogleOauth2UserInfo(attributes);
            default -> throw new IllegalAccessError("잘못된 제공자 입니다.");
        };
    }
}
