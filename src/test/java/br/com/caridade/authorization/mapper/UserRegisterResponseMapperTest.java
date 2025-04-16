package br.com.caridade.authorization.mapper;

import br.com.caridade.authorization.dto.UserGroupRoleDTO;
import br.com.caridade.authorization.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class UserRegisterResponseMapperTest {

    @InjectMocks
    private UserRegisterResponseMapper mapper;

    @Mock
    private UserGroupRoleMapper groupRoleMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toDTO() {
       final var permission1 = new Permission();
       final var permission2 = new Permission();
       final var role = new Role();
       final var group = new Group();
       final var user = new User();
       final var rbg = new UserRoleGroup();

       permission1.setName("CREATE_CAMPAIGN");
       permission2.setName("VIEW_GROUP");

       role.setName("ADMIN");
       role.setPermissions(Set.of(permission1, permission2));

       group.setName("Caridade");

       user.setId(1);
       user.setEmail("jaime@caridade.com");
       user.setActive(true);
       user.setRolesByGroup(Set.of(rbg));

       rbg.setUser(user);
       rbg.setGroup(group);
       rbg.setRole(role);

        when(groupRoleMapper.toDto(anyList()))
                .thenReturn(List.of(UserGroupRoleDTO.builder()
                        .groupName("Caridade")
                        .roleName("ADMIN")
                        .build()));

       var dto = mapper.toDTO(user);

       assertEquals(1, dto.getInternalId());
       assertEquals("jaime@caridade.com", dto.getEmail());
       assertEquals(List.of("ADMIN"), dto.getRoles());
       assertTrue(dto.getPermissions().contains("CREATE_CAMPAIGN"));

        assertEquals(1, dto.getUserGroupRoleDTOS().size());
        var groupDto = dto.getUserGroupRoleDTOS().get(0);
        assertEquals("Caridade", groupDto.getGroupName());
        assertEquals("ADMIN", groupDto.getRoleName());
    }
}