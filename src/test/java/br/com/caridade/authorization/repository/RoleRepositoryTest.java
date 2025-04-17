package br.com.caridade.authorization.repository;

import br.com.caridade.authorization.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void clean() {
        testEntityManager.getEntityManager()
                .createQuery("DELETE FROM Role")
                .executeUpdate();
    }

    @Test
    void shouldFindRoleByName() {
        Role role = new Role();
        role.setName("ROLE1");

        testEntityManager.persistAndFlush(role);

        Optional<Role> found = roleRepository.findByName("ROLE1");

        assertEquals("ROLE1", found.get().getName());
    }

}