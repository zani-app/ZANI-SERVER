package com.zani.zani.security.application.service;

import com.zani.zani.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.service.AccountService;
import com.zani.zani.security.info.CustomUserPrincipal;
import com.zani.zani.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public CustomUserPrincipal execute(UUID accountId) {
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        return accountService.createCustomUserPrincipalByAccount(account);
    }
}
