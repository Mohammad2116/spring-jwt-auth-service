package ir.aspireapps.authservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Refresh token request payload")
public record RefreshTokenRequest(
        @Schema(example = "refresh-token-value", description = "Refresh token")
        @NotBlank
        @Size(max = 500)
        String refreshToken
) {}
