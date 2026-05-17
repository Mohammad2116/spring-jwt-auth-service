package ir.aspireapps.authservice.service;

import ir.aspireapps.authservice.dto.auth.AuthResponse;
import ir.aspireapps.authservice.dto.auth.LoginRequest;
import ir.aspireapps.authservice.dto.auth.RefreshTokenRequest;
import ir.aspireapps.authservice.dto.user.UserRegisterRequest;
import ir.aspireapps.authservice.exception.ResourceNotFoundException;
import ir.aspireapps.authservice.model.RefreshToken;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.security.CustomUserDetails;
import ir.aspireapps.authservice.security.JwtService;
import ir.aspireapps.authservice.security.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Transactional(readOnly = true)
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(UserRegisterRequest request,
                                 String device,
                                 String ip){
        User user = userService.register(request);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user, device, ip);

        return new AuthResponse(accessToken,
                refreshToken,
                "Bearer ",
                jwtService.getAccessTokenExpirationSeconds());
    }

    @Transactional
    public AuthResponse login(@Valid LoginRequest request,
                              String device,
                              String ip) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user, device, ip);
        return new AuthResponse(accessToken, refreshToken, "Bearer ", jwtService.getAccessTokenExpirationSeconds());
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request,
                                String device,
                                String ip){
        RefreshToken oldToken = refreshTokenService.verifyRefreshToken(request.refreshToken());

        User user = oldToken.getUser();

        refreshTokenService.markAsUsed(oldToken);
        refreshTokenService.revokeToken(oldToken);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = refreshTokenService.createRefreshToken(user, device, ip);

        return new AuthResponse(newAccessToken, newRefreshToken, "Bearer ", jwtService.getAccessTokenExpirationSeconds());
    }

    @Transactional
    public void logout(String refreshTokenValue){
        RefreshToken refreshtoken = refreshTokenService.verifyRefreshToken(refreshTokenValue);
        refreshTokenService.revokeToken(refreshtoken);
    }

    @Transactional
    public void logoutAll(String email){
        refreshTokenService.revokeAllUserTokens(email);
    }
}
