package ir.aspireapps.authservice.dto.error;

import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Builder
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String code,
        String message,
        String path,
        Map<String, String> errors
) {}
