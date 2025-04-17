package br.com.caridade.authorization.mapper;

import br.com.caridade.authorization.dto.UserGroupRoleDTO;
import br.com.caridade.authorization.model.UserRoleGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGroupRoleMapper {

    public List<UserGroupRoleDTO> toDto(List<UserRoleGroup> userRoleGroups) {
        return userRoleGroups.stream()
                .map(urg -> UserGroupRoleDTO.builder()
                        .groupName(urg.getGroup().getName())
                        .roleName(urg.getRole().getName())
                        .build())
                .toList();
    }
}
