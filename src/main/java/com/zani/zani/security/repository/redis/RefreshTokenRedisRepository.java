package com.zani.zani.security.repository.redis;

import com.zani.zani.security.repository.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByValue(String value);
}
