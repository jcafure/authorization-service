package br.com.caridade.authorization.serviceimpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl serviceImpl;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticated() {
        final var authentication = new UsernamePasswordAuthenticationToken("jaime@email", "123465");

        Mockito.when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        Mockito.when(jwtService.generateToken(authentication)).thenReturn("token");

        String tokenResponse = serviceImpl.authenticated(authentication);

        Assertions.assertThat(tokenResponse).isNotEmpty();

    }

    @Test
    void testAuthenticatedFails(){
        final var authentication = new UsernamePasswordAuthenticationToken("bad@email", "123465");
        Mockito.when(authenticationManager.authenticate(authentication)).thenThrow(new RuntimeException("Credenciais inválidas"));

        assertThrows(RuntimeException.class, () -> {
            serviceImpl.authenticated(authentication);
        });

        verify(authenticationManager).authenticate(authentication);
        verify(jwtService, never()).generateToken(any());
    }
}