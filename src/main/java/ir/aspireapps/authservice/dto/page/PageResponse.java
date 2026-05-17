package ir.aspireapps.authservice.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(description = "Paginated response wrapper")
public record PageResponse<T>(
        @Schema(description = "List of items")
        List<T> data,
        @Schema(example = "0")
        int page,
        @Schema(example = "10")
        int size,
        @Schema(example = "25")
        long totalElements,
        @Schema(example = "3")
        long totalPages,
        @Schema(example = "true")
        boolean first,
        @Schema(example = "false")
        boolean last) {}
