package com.zani.zani.security.repository;

import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.type.ESecurityProvider;

import java.util.UUID;

public interface AccountRepository {

    Account findByIdOrElseThrow(UUID accountId);

    void save(Account account);

    void deleteById(UUID accountId);

    Account findBySerialIdAndProviderOrElseThrow(String serialId, ESecurityProvider provider);

    Account findBySerialIdAndProviderOrElseNull(String serialId, ESecurityProvider provider);

    void existsBySerialIdAndProviderThenThrow(String serialId, ESecurityProvider provider);
}
