package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.dto.RegisterLoginDto;
import br.com.caridade.authorization.dto.UserRegisterResponseDTO;
import br.com.caridade.authorization.exception.EmailAlreadyInUseException;
import br.com.caridade.authorization.mapper.UserRegisterResponseMapper;
import br.com.caridade.authorization.model.Group;
import br.com.caridade.authorization.model.Role;
import br.com.caridade.authorization.model.User;
import br.com.caridade.authorization.model.UserRoleGroup;
import br.com.caridade.authorization.repository.GroupRepository;
import br.com.caridade.authorization.repository.RoleRepository;
import br.com.caridade.authorization.repository.UserRepository;
import br.com.caridade.authorization.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private static final String GRUPO_CARIDADE = "Grupo Caridade";
    private static final String GRUPO_GRAO_DE_MOSTARDA = "Grupo Grão de Mostarda";
    private static final String NAME_ROLE_DONOR = "DONOR";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisterResponseMapper mapper;

    @Override
    public UserRegisterResponseDTO registerUser(RegisterLoginDto request) {
        hasEmail(request);
        var user = createUser(request);
        getDefaultGroups().forEach(group ->
                user.getRolesByGroup().add(buildUserRoleGroup(user, getDefaultRole(), group)));

        return mapper.toDTO(userRepository.save(user));
    }

    private void hasEmail(RegisterLoginDto request) {
        if (findByEmailWithRoles(request.getEmail()).isPresent()) {
            log.warn("Tentativa de cadastro com e-mail já em uso: {}", request.getEmail());
            throw new EmailAlreadyInUseException(request.getEmail());
        }
    }

    @Override
    public Optional<User> findByEmailWithRoles(String email) {
        return userRepository.findByEmailWithRoles(email);
    }

    private User createUser(RegisterLoginDto request) {
        final var user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setCreatedAt(LocalDate.now());
        user.setUpdateAt(LocalDate.now());
        return user;
    }

    private List<Group> getDefaultGroups() {
        return groupRepository.findByNameIn(List.of(GRUPO_CARIDADE, GRUPO_GRAO_DE_MOSTARDA));
    }

    private UserRoleGroup buildUserRoleGroup(User user, Role role, Group group) {
        final var urg = new UserRoleGroup();
        urg.setUser(user);
        urg.setRole(role);
        urg.setGroup(group);
        return urg;
    }

    private Role getDefaultRole() {
        return roleRepository.findByName(NAME_ROLE_DONOR)
                .orElseThrow(() -> new RuntimeException("Role padrão 'Doador' não encontrada."));
    }
}