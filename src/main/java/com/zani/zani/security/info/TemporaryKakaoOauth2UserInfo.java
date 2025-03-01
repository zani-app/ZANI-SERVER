package com.zani.zani.security.info;

import com.zani.zani.security.info.factory.Oauth2UserInfo;

import java.util.Map;

public class TemporaryKakaoOauth2UserInfo extends Oauth2UserInfo {
    public TemporaryKakaoOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }
}
