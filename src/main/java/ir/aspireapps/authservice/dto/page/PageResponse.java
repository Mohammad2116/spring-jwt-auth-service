package ir.aspireapps.authservice.dto.page;

import java.util.List;

public record PageResponse<T>(
        List<T> data,
        int page,
        int size,
        long totalElements,
        long totalPages,
        boolean first,
        boolean last) {}
