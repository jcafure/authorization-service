package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtServiceImplTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        final var user = mock(User.class);
        when(user.getId()).thenReturn(1);
        when(user.getEmail()).thenReturn("jaime@caridade.com");
        when(user.getPassword()).thenReturn("123");
        when(user.isActive()).thenReturn(true);
        when(user.getRolesByGroup()).thenReturn(Set.of());

        UserAuthenticatedImpl userDetails = new UserAuthenticatedImpl(user);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getName()).thenReturn("jaime@caridade.com");
        when(authentication.getAuthorities()).thenReturn(List.of());

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("fake-token");

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        String token = jwtService.generateToken(authentication);

        assertEquals("fake-token", token);
        verify(jwtEncoder).encode(any());
    }

}