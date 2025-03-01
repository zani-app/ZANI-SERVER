package com.zani.zani.account.repository;

import com.zani.zani.account.domain.User;

import java.util.UUID;

public interface UserRepository {

    User findByIdOrElseThrow(UUID userId);

    void save(User user);

    boolean existsByNickname(String nickname);
}
