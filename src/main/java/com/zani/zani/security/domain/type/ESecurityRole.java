package com.zani.zani.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESecurityRole {

    USER("유저", "USER", "ROLE_USER"),
    ADMIN("관리자", "ADMIN", "ROLE_ADMIN"),
    GUEST("게스트", "GUEST", "ROLE_GUEST")

    ;

    private final String koName;
    private final String enName;
    private final String securityName;

    public static ESecurityRole fromString(String value) {
        if (value == null) {
            throw new SecurityException("Security Role이 null입니다.");
        }
        return switch (value.toUpperCase()) {
            case "USER" -> USER;
            case "ADMIN" -> ADMIN;
            default -> throw new IllegalArgumentException("Security Role이 잘못되었습니다.");
        };
    }
}
