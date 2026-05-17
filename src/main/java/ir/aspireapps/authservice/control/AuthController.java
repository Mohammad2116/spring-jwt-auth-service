package ir.aspireapps.authservice.control;

import ir.aspireapps.authservice.dto.auth.AuthResponse;
import ir.aspireapps.authservice.dto.auth.LoginRequest;
import ir.aspireapps.authservice.dto.auth.RefreshTokenRequest;
import ir.aspireapps.authservice.dto.user.UserRegisterRequest;
import ir.aspireapps.authservice.model.RefreshToken;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.service.AuthService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ServletRequest httpServletRequest;

    @PostMapping("/register")
    public AuthResponse save(@Valid @RequestBody UserRegisterRequest request,
                             HttpServletRequest httpServletRequest){
        return authService.register(request,
                                    httpServletRequest.getHeader("User-Agent"),
                                    httpServletRequest.getRemoteAddr());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request,
                              HttpServletRequest httpServletRequest){
        return authService.login(request,
                                 httpServletRequest.getHeader("User-Agent"),
                                 httpServletRequest.getRemoteAddr());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request){
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(@Valid @RequestBody RefreshTokenRequest request,
                                          @AuthenticationPrincipal User user){
        authService.logoutAll(request.refreshToken(), user.getEmail());
        return ResponseEntity.noContent().build();
    }
}
