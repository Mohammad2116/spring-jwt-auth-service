package ir.aspireapps.authservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Login request payload")
public record LoginRequest(
        @Schema(example = "jack@example.com", description = "User email")
        @NotBlank(message = "Email can't be null")
        @Email(message = "Email not valid")
        String email,

        @Schema(example = "Password1234", description = "User password")
        @NotBlank(message = "Password can't be null")
        String password
) {}
