package br.com.caridade.authorization.repository;

import br.com.caridade.authorization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
    SELECT DISTINCT u FROM User u
    LEFT JOIN FETCH u.rolesByGroup rbg
    LEFT JOIN FETCH rbg.role r
    LEFT JOIN FETCH rbg.group g
    LEFT JOIN FETCH r.permissions p
    WHERE u.email = :email
    """)
    Optional<User> findByEmailWithRoles(@Param("email") String email);
}
