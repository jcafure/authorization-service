package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.dto.RegisterLoginDto;
import br.com.caridade.authorization.dto.UserGroupRoleDTO;
import br.com.caridade.authorization.dto.UserRegisterResponseDTO;
import br.com.caridade.authorization.mapper.UserRegisterResponseMapper;
import br.com.caridade.authorization.model.Group;
import br.com.caridade.authorization.model.Permission;
import br.com.caridade.authorization.model.Role;
import br.com.caridade.authorization.model.User;
import br.com.caridade.authorization.model.UserRoleGroup;
import br.com.caridade.authorization.repository.GroupRepository;
import br.com.caridade.authorization.repository.RoleRepository;
import br.com.caridade.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRegisterResponseMapper mapper;
    @InjectMocks
    private RegisterServiceImpl registerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(RegisterServiceImpl.class);
    }

    @Test
    void testRegisterUser() {
        final var role = new Role();
        final var permission = new Permission();
        final var permission02 = new Permission();
        final var group = new Group();
        final var group02 = new Group();
        final var registerLogin = new RegisterLoginDto();
        final var user = new User();
        final var responseDTO = new UserRegisterResponseDTO();

        group.setName("Grupo Caridade");
        group02.setName("Grupo Grão de Mostarda");

        registerLogin.setPassword("12346");
        registerLogin.setEmail("xaime@gmail");

        permission.setName("VIEW");
        permission02.setName("DONATE");
        role.setName("DONOR");
        role.setPermissions(Set.of(permission, permission02));

        responseDTO.setInternalId(1);
        responseDTO.setEmail("xaime@gmail");
        responseDTO.setRoles(List.of("DONOR"));
        responseDTO.setPermissions(List.of("VIEW", "DONATE"));
        responseDTO.setUserGroupRoleDTOS(List.of(
                UserGroupRoleDTO.builder().groupName("Grupo Caridade").roleName("DONOR").build(),
                UserGroupRoleDTO.builder().groupName("Grupo Grão de Mostarda").roleName("DONOR").build()
        ));

        Mockito.when(userRepository.findByEmailWithRoles(registerLogin.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(registerLogin.getPassword()))
                .thenReturn("encodedPassword");
        Mockito.when(groupRepository.findByNameIn(List.of("Grupo Caridade", "Grupo Grão de Mostarda")))
                .thenReturn(List.of(group, group02));
        Mockito.when(roleRepository.findByName("DONOR"))
                .thenReturn(Optional.of(role));

        user.setId(1);
        user.setEmail(registerLogin.getEmail());
        user.setPassword("encodedPassword");
        user.setRolesByGroup(Set.of(
                buildUserRoleGroup(user, role, group),
                buildUserRoleGroup(user, role, group02)
        ));

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);
        Mockito.when(mapper.toDTO(Mockito.any(User.class)))
                .thenReturn(responseDTO);

        var response = registerService.registerUser(registerLogin);

        assertEquals("xaime@gmail", response.getEmail());
        assertEquals(1, response.getInternalId());
        assertEquals(List.of("DONOR"), response.getRoles());
        assertTrue(response.getPermissions().contains("VIEW"));
        assertTrue(response.getPermissions().contains("DONATE"));
        assertEquals(2, response.getUserGroupRoleDTOS().size());
    }

    @Test
    void testFindByEmailWithRoles() {
        final var registerLogin = new RegisterLoginDto();
        final var user = new User();

        registerLogin.setEmail("jaime@caridade.com");
        user.setEmail(registerLogin.getEmail());
        user.setPassword("encoded-password");

        Mockito.when(userRepository.findByEmailWithRoles(registerLogin.getEmail()))
                .thenReturn(Optional.of(user));

        Optional<User> result = registerService.findByEmailWithRoles(registerLogin.getEmail());

        assertTrue(result.isPresent());
        assertEquals("jaime@caridade.com", result.get().getEmail());
    }

    @Test
    void testFindByEmailWithRolesReturnsEmpty() {
        final var email = "inexistente@email.com";
        Mockito.when(userRepository.findByEmailWithRoles(email))
                .thenReturn(Optional.empty());

        Optional<User> result = registerService.findByEmailWithRoles(email);
        assertTrue(result.isEmpty());
    }

    private static UserRoleGroup buildUserRoleGroup(User user, Role role, Group group) {
        final var userRoleGroup = new UserRoleGroup();
        userRoleGroup.setUser(user);
        userRoleGroup.setRole(role);
        userRoleGroup.setGroup(group);
        return userRoleGroup;
    }
}