package br.com.caridade.authorization.mapper;

import br.com.caridade.authorization.dto.UserGroupRoleDTO;
import br.com.caridade.authorization.dto.UserRegisterResponseDTO;
import br.com.caridade.authorization.model.Permission;
import br.com.caridade.authorization.model.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class UserRegisterResponseMapper {
    private final UserGroupRoleMapper groupRoleMapper;

    public UserRegisterResponseDTO toDTO(User user) {
        List<String> roles = buildRoles(user);
        List<String> permissions = getPermissions(user);

        List<UserGroupRoleDTO> groups = groupRoleMapper
                .toDto(user.getRolesByGroup().stream().toList());

        return UserRegisterResponseDTO.builder()
                .internalId(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .permissions(permissions)
                .userGroupRoleDTOS(groups)
                .build();
    }

    private static List<String> buildRoles(User user) {
        return user.getRolesByGroup().stream()
                .map(rbg -> rbg.getRole().getName())
                .distinct()
                .toList();
    }

    private static List<String> getPermissions(User user) {
        return user.getRolesByGroup().stream()
                .flatMap(rbg -> rbg.getRole().getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();
    }
}
