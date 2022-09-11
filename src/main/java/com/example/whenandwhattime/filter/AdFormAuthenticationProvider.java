package com.example.whenandwhattime.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.whenandwhattime.entity.Admin;
import com.example.whenandwhattime.repository.AdminRepository;

@Configuration
public class AdFormAuthenticationProvider implements AuthenticationProvider {

    protected static Logger log = LoggerFactory.getLogger(AdFormAuthenticationProvider.class);

    @Autowired
    private AdminRepository repository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        log.debug("name={}", name);
        log.debug("password={}", password);

        if ("".equals(name) || "".equals(password)) {
            throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");
        }

        Admin entity = repository.findByUsername(name);
        if (entity == null) {
            throw new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。");
        }

        return new UsernamePasswordAuthenticationToken(entity, password, entity.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}