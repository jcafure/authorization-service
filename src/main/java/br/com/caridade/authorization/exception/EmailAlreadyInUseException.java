package br.com.caridade.authorization.exception;

public class EmailAlreadyInUseException extends RuntimeException{

    public EmailAlreadyInUseException(String email){
        super("O e-mail '" + email + "' já está em uso.");
    }
}
