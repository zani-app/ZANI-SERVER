package com.zani.zani.security.info;

import com.zani.zani.security.info.factory.Oauth2UserInfo;

import java.util.Map;

public class TemporaryGoogleOauth2UserInfo extends Oauth2UserInfo {
    public TemporaryGoogleOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }
}
