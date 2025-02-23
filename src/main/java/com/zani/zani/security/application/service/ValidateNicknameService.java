package com.zani.zani.security.application.service;

import com.zani.zani.account.repository.UserRepository;
import com.zani.zani.security.application.dto.response.ValidationResponseDto;
import com.zani.zani.security.application.usecase.ValidateNicknameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateNicknameService implements ValidateNicknameUseCase {

    private final UserRepository userRepository;

    @Override
    public ValidationResponseDto execute(String nickname) {
        return ValidationResponseDto.of(
                !userRepository.existsByNickname(nickname)
        );
    }
}
