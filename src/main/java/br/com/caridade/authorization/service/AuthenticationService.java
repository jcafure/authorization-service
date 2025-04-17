package br.com.caridade.authorization.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    String authenticated(Authentication authentication);
}
