package ir.aspireapps.authservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends AuthServiceBaseException {
    public InvalidInputException(String message){
        super(message, HttpStatus.BAD_REQUEST, "INVALID_INPUT");
    }
}
