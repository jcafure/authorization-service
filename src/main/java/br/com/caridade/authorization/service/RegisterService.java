package br.com.caridade.authorization.service;

import br.com.caridade.authorization.dto.RegisterLoginDto;
import br.com.caridade.authorization.dto.UserRegisterResponseDTO;
import br.com.caridade.authorization.model.User;

import java.util.Optional;
public interface RegisterService {

  UserRegisterResponseDTO registerUser(RegisterLoginDto registerLoginDto);
  Optional<User> findByEmailWithRoles(String email);
}
