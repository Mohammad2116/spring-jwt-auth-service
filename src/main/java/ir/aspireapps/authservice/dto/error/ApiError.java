package ir.aspireapps.authservice.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard error response")
@Builder
public record ApiError(
        @Schema(example = "2026-05-17T10:15:30Z")
        Instant timestamp,
        @Schema(example = "401")
        int status,
        @Schema(example = "Unauthorized")
        String error,
        @Schema(example = "UNAUTHORIZED_ERROR")
        String code,
        @Schema(example = "Invalid email or password")
        String message,
        @Schema(example = "/api/auth/login")
        String path,
        @Schema(example = "{'email', 'not valid'}")
        Map<String, String> errors
) {}
