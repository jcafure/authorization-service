package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserAuthenticatedImplTest {

    @InjectMocks
    private UserAuthenticatedImpl userDetails;

    private User user;
    private Permission permission01;
    private Permission permission02;
    private Role role;
    private Group group;
    private UserRoleGroup userRoleGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        permission01 = new Permission();
        permission02 = new Permission();
        role = new Role();
        group = new Group();
        userRoleGroup = new UserRoleGroup();

        user.setEmail("jaime@caridade.com");
        user.setPassword("senha123");
        user.setActive(true);
        permission01.setName("VIEW");
        permission02.setName("EDIT");
        role.setName("ADMIN");
        role.setPermissions(Set.of(permission01, permission02));
        group.setName("Grupo Caridade");
        userRoleGroup.setUser(user);
        userRoleGroup.setGroup(group);
        userRoleGroup.setRole(role);
        user.setRolesByGroup(Set.of(userRoleGroup));
        userDetails = new UserAuthenticatedImpl(user);
    }

    @Test
    void getAuthorities() {
        var authorities = userDetails.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("VIEW")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("EDIT")));
    }

    @Test
    void getPassword() {
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("jaime@caridade.com", userDetails.getUsername());
    }

    @Test
    void isEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}