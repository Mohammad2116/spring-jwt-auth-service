package ir.aspireapps.authservice.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email can't be null")
        @Email(message = "Email not valid")
        String email,

        @NotBlank(message = "Password can't be null")
        String password
) {}
