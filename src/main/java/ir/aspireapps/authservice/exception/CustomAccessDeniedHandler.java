package ir.aspireapps.authservice.exception;

import ir.aspireapps.authservice.dto.error.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ApiError error = ApiError.builder()
                .timestamp(Instant.now())
                .status(403)
                .error("Forbidden")
                .code("ACCESS_DENIED")
                .message("You do not have permission")
                .path(request.getRequestURI())
                .build();
        response.getWriter().println(mapper.writeValueAsString(error));
    }
}
