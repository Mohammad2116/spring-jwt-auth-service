package ir.aspireapps.authservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends AuthServiceBaseException {
    public InvalidTokenException(String message){
        super(message, HttpStatus.UNAUTHORIZED, "INVALID_TOKEN");
    }
}
