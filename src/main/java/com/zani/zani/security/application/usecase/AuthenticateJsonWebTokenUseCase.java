package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.info.CustomUserPrincipal;

import java.util.UUID;

@UseCase
public interface AuthenticateJsonWebTokenUseCase {

    CustomUserPrincipal execute(UUID accountId);
}
