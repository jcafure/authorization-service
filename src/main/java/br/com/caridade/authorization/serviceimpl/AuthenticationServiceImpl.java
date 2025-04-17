package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    @Override
    public String authenticated(Authentication authentication) {
        try {
            Authentication authenticated = authenticationManager.authenticate(authentication);
            log.debug("Autenticado: {}", authenticated.getName());
            return jwtService.generateToken(authenticated);
        } catch (Exception e) {
            log.error("Erro na autenticação: {} - {}", e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

}
