package br.com.caridade.authorization.serviceimpl;

import br.com.caridade.authorization.dto.UserGroupRoleDTO;
import br.com.caridade.authorization.model.Permission;
import br.com.caridade.authorization.model.User;
import br.com.caridade.authorization.model.UserRoleGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserAuthenticatedImpl implements UserDetails {
    private final User user;

    public UserAuthenticatedImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRolesByGroup().stream()
                .map(UserRoleGroup::getRole)
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public Integer getId() {
        return user.getId();
    }

    public List<String> getRoles() {
        return user.getRolesByGroup().stream()
                .map(roleGroup -> roleGroup.getRole().getName())
                .distinct()
                .toList();
    }

    public List<String> getPermissions() {
        return user.getRolesByGroup().stream()
                .map(UserRoleGroup::getRole)
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();
    }

    public List<UserGroupRoleDTO> getUserGroupRoleDTOS() {
        return user.getRolesByGroup().stream()
                .map(roleGroup -> new UserGroupRoleDTO(
                        roleGroup.getGroup().getId(),
                        roleGroup.getGroup().getName(),
                        roleGroup.getRole().getName()
                ))
                .toList();
    }

}
