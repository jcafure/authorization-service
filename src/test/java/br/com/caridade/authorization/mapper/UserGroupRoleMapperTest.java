package br.com.caridade.authorization.mapper;

import br.com.caridade.authorization.dto.UserGroupRoleDTO;
import br.com.caridade.authorization.model.Group;
import br.com.caridade.authorization.model.Role;
import br.com.caridade.authorization.model.UserRoleGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class UserGroupRoleMapperTest {

    @InjectMocks
    private UserGroupRoleMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDto() {
        final var group1 = new Group();
        group1.setName("Grupo Caridade");

        final var role1 = new Role();
        role1.setName("DONOR");

        final var urg1 = new UserRoleGroup();
        urg1.setGroup(group1);
        urg1.setRole(role1);

        final var group2 = new Group();
        group2.setName("Grupo Grão de Mostarda");

        final var role2 = new Role();
        role2.setName("DONOR");

        final var urg2 = new UserRoleGroup();
        urg2.setGroup(group2);
        urg2.setRole(role2);

        List<UserRoleGroup> input = List.of(urg1, urg2);

        List<UserGroupRoleDTO> dtos = mapper.toDto(input);

        assertThat(dtos).hasSize(2);
        assertThat(dtos).extracting(UserGroupRoleDTO::getGroupName)
                .containsExactlyInAnyOrder("Grupo Caridade", "Grupo Grão de Mostarda");
        assertThat(dtos).allMatch(dto -> dto.getRoleName().equals("DONOR"));


    }
}