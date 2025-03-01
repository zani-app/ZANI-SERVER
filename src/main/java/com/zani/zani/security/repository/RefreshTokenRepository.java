package com.zani.zani.security.repository;

import com.zani.zani.security.repository.entity.redis.RefreshToken;

import java.util.UUID;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    void deleteById(UUID value);

    RefreshToken findByValueOrElseThrow(String value);
}
