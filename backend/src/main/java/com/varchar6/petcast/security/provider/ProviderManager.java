package com.varchar6.petcast.security.provider;

import com.varchar6.petcast.security.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProviderManager implements AuthenticationManager {
    private final List<AuthenticationProvider> providerList;

    @Autowired
    public ProviderManager(List<AuthenticationProvider> providerList) {
        this.providerList = providerList;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : providerList) {
            if (provider.supports(authentication.getClass())) {
                return provider.authenticate(authentication);
            }
        }

        // 인증 실패 시 예외 처리
        throw new AuthenticationException("No provider found for " + authentication.getClass().getName());
    }
}