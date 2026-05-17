package ir.aspireapps.authservice.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @NotBlank
        @Size(max = 500)
        String refreshToken
) {}
