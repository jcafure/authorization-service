package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;

    @Override
    public String generateToken(Authentication authentication) {
        final var now = Instant.now();
        final var expiry = 3600L;
        final var scopes = buildScopes(authentication);
        final var claims = buildClaims(authentication, now, expiry, scopes);
        log.debug("Authorities: {}", authentication.getAuthorities());
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private static JwtClaimsSet buildClaims(Authentication authentication, Instant now,
                                            long expiry, String scopes) {

        UserAuthenticatedImpl  user = (UserAuthenticatedImpl) authentication.getPrincipal();

        return JwtClaimsSet.builder()
                .issuer("auth-service")
                .expiresAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .claims(claims -> {
                    claims.put("internalId", user.getId());
                    claims.put("roles", user.getRoles());
                    claims.put("permissions", user.getPermissions());
                    claims.put("userGroupRoleDTOS", user.getUserGroupRoleDTOS());
                }).build();
    }

    private static String buildScopes(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
