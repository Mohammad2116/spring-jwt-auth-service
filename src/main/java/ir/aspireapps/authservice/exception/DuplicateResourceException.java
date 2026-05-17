package ir.aspireapps.authservice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends AuthServiceBaseException {
    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT, "DUPLICATE_RESOURCE");
    }
}
