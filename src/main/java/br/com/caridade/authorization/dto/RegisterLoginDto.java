package br.com.caridade.authorization.dto;
import lombok.Data;

@Data
public class RegisterLoginDto {

    private String email;
    private String password;
    private boolean isOrganizer = false;
}