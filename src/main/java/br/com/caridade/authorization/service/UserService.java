package br.com.caridade.authorization.service;

import br.com.caridade.authorization.model.User;

import java.util.Optional;
public interface UserService {

  User createUser(User user);

  Optional<User> findUserByEmail(String email);
}
