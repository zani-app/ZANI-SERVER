package com.zani.zani.security.application.service;

import com.zani.zani.account.domain.User;
import com.zani.zani.account.repository.UserRepository;
import com.zani.zani.core.constant.Constants;
import com.zani.zani.core.utility.JsonWebTokenUtil;
import com.zani.zani.security.application.dto.request.SignUpOauthRequestDto;
import com.zani.zani.security.application.usecase.SignUpOauthUseCase;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignUpOauthService implements SignUpOauthUseCase {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${cloud.aws.s3.user-default-img-url}")
    private String userDefaultImgUrl;

    @Override
    @Transactional
    public void execute(String temporaryToken, SignUpOauthRequestDto requestDto) {

        // temporary Token 파싱
        Claims claims = jsonWebTokenUtil.validateToken(temporaryToken);

        // claims 으로부터 serialId, provider 추출
        String[] split = claims.get(Constants.ACCOUNT_ID_CLAIM_NAME,String.class).split(":");
        String serialId = split[0];
        ESecurityProvider provider = ESecurityProvider.valueOf(split[1]);

        // 중복된 아이디인지 확인
        accountRepository.existsBySerialIdAndProviderThenThrow(serialId, provider);

        // 유저 생성 및 저장
        User user = User.builder()
                .serialId(serialId)
                .provider(provider)
                .password(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()))
                .deviceToken(null)
                .notificationConfig(true)
                .createdAt(LocalDateTime.now())
                .nickname(requestDto.nickname())
                .profileImgUrl(userDefaultImgUrl)
                .build();

        userRepository.save(user);
    }
}
