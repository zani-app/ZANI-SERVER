package com.zani.zani.security.domain.service;

import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.repository.entity.mysql.AccountEntity;
import com.zani.zani.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public CustomUserPrincipal createCustomUserPrincipalByAccount(Account accountEntity) {
        return CustomUserPrincipal.create(accountEntity);
    }
}
