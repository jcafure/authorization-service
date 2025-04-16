package br.com.caridade.authorization.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

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
        when(authentication.getName()).thenReturn("jaime@caridade.com");

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("fake-token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        String token = jwtService.generateToken(authentication);

        assertEquals("fake-token", token);
        verify(jwtEncoder).encode(any());
    }
}