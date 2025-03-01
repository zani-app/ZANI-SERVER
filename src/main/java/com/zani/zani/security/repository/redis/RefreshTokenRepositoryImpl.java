package com.zani.zani.security.repository.redis;

import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.security.repository.RefreshTokenRepository;
import com.zani.zani.security.repository.entity.redis.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(refreshToken);
    }

    @Override
    public void deleteById(UUID id) {
        refreshTokenRedisRepository.deleteById(id);
    }

    @Override
    public RefreshToken findByValueOrElseThrow(String value) {
        return refreshTokenRedisRepository.findByValue(value)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
    }
}
