package ir.aspireapps.authservice.exception;

import ir.aspireapps.authservice.dto.error.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;

@Component
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ApiError error = ApiError.builder()
                .timestamp(Instant.now())
                .status(401)
                .error("Unauthorized")
                .code("UNAUTHORIZED")
                .message("Authentication required")
                .path(request.getRequestURI())
                .build();
        response.getWriter().println(mapper.writeValueAsString(error));
    }
}
