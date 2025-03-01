package com.zani.zani.security.application.service;

import com.zani.zani.security.application.usecase.AuthenticateUserNameUseCase;
import com.zani.zani.security.domain.Account;
import com.zani.zani.security.domain.service.AccountService;
import com.zani.zani.security.domain.type.ESecurityProvider;
import com.zani.zani.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserNameService implements AuthenticateUserNameUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String serialId) throws UsernameNotFoundException {
        Account account = accountRepository.findBySerialIdAndProviderOrElseThrow(serialId, ESecurityProvider.DEFAULT);

        return accountService.createCustomUserPrincipalByAccount(account);
    }
}
