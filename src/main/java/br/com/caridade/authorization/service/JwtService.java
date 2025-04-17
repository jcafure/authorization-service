package br.com.caridade.authorization.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String generateToken (Authentication authentication);
}
