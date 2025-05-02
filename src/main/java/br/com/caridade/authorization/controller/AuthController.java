package br.com.caridade.authorization.controller;

import br.com.caridade.authorization.dto.LoginRequestDto;
import br.com.caridade.authorization.dto.RegisterLoginDto;
import br.com.caridade.authorization.dto.UserRegisterResponseDTO;
import br.com.caridade.authorization.serviceimpl.AuthenticationServiceImpl;
import br.com.caridade.authorization.serviceimpl.RegisterServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@AllArgsConstructor
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;
    private final RegisterServiceImpl registerServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestBody RegisterLoginDto request) {
        return ResponseEntity.ok(registerServiceImpl.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequestDto request) {
        var authRequest = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );

        String token = authenticationService.authenticated(authRequest);
        return ResponseEntity.ok(token);
    }
}
