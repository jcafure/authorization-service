package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.model.User;
import br.com.caridade.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserDetailsWhenUserExists() {
        final var user = new User();
        user.setEmail("jaime@caridade.com");
        user.setPassword("senha123");

        when(userRepository.findByEmailWithRoles("jaime@caridade.com"))
                .thenReturn(Optional.of(user));

        final var userDetails = userDetailsService.loadUserByUsername("jaime@caridade.com");

        assertNotNull(userDetails);
        assertEquals("jaime@caridade.com", userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }
}