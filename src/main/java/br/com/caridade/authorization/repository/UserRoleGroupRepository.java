package br.com.caridade.authorization.repository;

import br.com.caridade.authorization.model.UserRoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleGroupRepository extends JpaRepository<UserRoleGroup, Integer> {
}
