package br.com.caridade.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponseDTO {

    private Integer internalId;
    private String email;
    private List<String> roles;
    private List<String> permissions;
    private List<UserGroupRoleDTO> userGroupRoleDTOS = new ArrayList<>();
}
