package ir.aspireapps.authservice.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.authservice.dto.auth.AuthResponse;
import ir.aspireapps.authservice.dto.auth.LoginRequest;
import ir.aspireapps.authservice.dto.auth.RefreshTokenRequest;
import ir.aspireapps.authservice.dto.user.UserRegisterRequest;
import ir.aspireapps.authservice.model.RefreshToken;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.security.CustomUserDetails;
import ir.aspireapps.authservice.service.AuthService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "Register, login, refresh, and logout operations"
)
public class AuthController {
    private final AuthService authService;
    private final ServletRequest httpServletRequest;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns authentication tokens."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "User already exists")
    })
    @PostMapping("/register")
    public AuthResponse save(@Valid @RequestBody UserRegisterRequest request,
            HttpServletRequest httpServletRequest){
        return authService.register(request,
                                    httpServletRequest.getHeader("User-Agent"),
                                    httpServletRequest.getRemoteAddr());
    }

    @Operation(
            summary = "Login",
            description = "Authenticates a user with email and passwrod and returns JWT tokens"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request,
                              HttpServletRequest httpServletRequest){
        return authService.login(request,
                                 httpServletRequest.getHeader("User-Agent"),
                                 httpServletRequest.getRemoteAddr());
    }

    @Operation(
            summary = "Logout",
            description = "Revoke the provided refresh token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request){
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Logout All",
            description = "Revoke all provided refresh tokens from all devices/IPs"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout all successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/logout/all")
    public ResponseEntity<Void> logoutAll(@AuthenticationPrincipal CustomUserDetails userDetails){
        authService.logoutAll(userDetails.getUser().getEmail());
        return ResponseEntity.noContent().build();
    }
}
