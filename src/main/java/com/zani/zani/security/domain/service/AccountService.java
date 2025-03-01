package com.zani.zani.security.domain.service;

import com.zani.zani.security.domain.Account;
import com.zani.zani.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public CustomUserPrincipal createCustomUserPrincipalByAccount(Account accountEntity) {
        return CustomUserPrincipal.create(accountEntity);
    }
}
