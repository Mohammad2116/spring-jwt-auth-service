package ir.aspireapps.authservice.mapper;

import ir.aspireapps.authservice.dto.page.PageResponse;
import org.springframework.data.domain.Page;

public class PageResponseMapper {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
