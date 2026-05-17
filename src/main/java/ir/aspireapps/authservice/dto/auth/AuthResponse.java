package ir.aspireapps.authservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response payload")
public record AuthResponse(
        @Schema(example = "eyJhbGcoioiJIldjkd...")
        String accessToken,

        @Schema(example = "refresh-token-value")
        String refreshToken,

        @Schema(example = "Bearer")
        String tokenType,

        @Schema(example = "2026-05-17T10:15:30Z")
        long expiresIn
) {}
