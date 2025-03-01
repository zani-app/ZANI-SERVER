package com.zani.zani.security.application.usecase;

import com.zani.zani.core.annotation.bean.UseCase;
import com.zani.zani.security.application.dto.request.UpdateDeviceTokenRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateDeviceTokenUseCase {

    void execute(
            UUID accountId,
            UpdateDeviceTokenRequestDto requestDto
    );
}
