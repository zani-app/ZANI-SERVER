package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.info.CustomUserPrincipal;

@UseCase
public interface LogoutUseCase {

    /**
     * Security 단에서 사용되는 Logout 유스케이스
     * @param principal UserPrincipal
     */
    void execute(CustomUserPrincipal principal);
}

