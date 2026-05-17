package ir.aspireapps.authservice.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AuthServiceBaseException {
    public ResourceNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }
}
