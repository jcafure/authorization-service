package br.com.caridade.authorization.repository;

import br.com.caridade.authorization.model.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void findByName() {
        final var group = new Group();
        group.setName("caridade");

        testEntityManager.persist(group);

        final var groupSaved = groupRepository.findByName("caridade");
        Assertions.assertNotNull(groupSaved);
    }

    @Test
    void findByNameIn() {
        final var group = new Group();
        final var group02 = new Group();

        group.setName("caridade");
        group02.setName("grão");

        testEntityManager.persist(group);
        testEntityManager.persist(group02);

        final var groupsSaved = groupRepository.findByNameIn(List.of("caridade", "grão"));

        Assertions.assertEquals(2, groupsSaved.size());
    }
}