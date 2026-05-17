package ir.aspireapps.authservice.security;

import ir.aspireapps.authservice.exception.InvalidTokenException;
import ir.aspireapps.authservice.exception.ResourceNotFoundException;
import ir.aspireapps.authservice.model.RefreshToken;
import ir.aspireapps.authservice.model.User;
import ir.aspireapps.authservice.repo.RefreshTokenRepository;
import ir.aspireapps.authservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHashService tokenHashService;
    private final UserRepository userRepository;

    @Value("${security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpirationMs;

    @Transactional
    public String createRefreshToken(User user,
                                     String device,
                                     String ip){
        String rawToken = generateSecureToken();
        String hash = tokenHashService.hash(rawToken);

        RefreshToken refreshToken = RefreshToken
                .builder()
                .tokenHash(hash)
                .user(user)
                .expiresAt(Instant.now().plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .used(false)
                .device(device)
                .ipAddress(ip)
                .build();
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    public RefreshToken verifyRefreshToken(String token){
        String hash = tokenHashService.hash(token);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(hash)
                .orElseThrow(()-> new InvalidTokenException("Invalid refresh token"));

        if(refreshToken.isRevoked())
            throw new InvalidTokenException("Refresh token revoked");
        if(refreshToken.isUsed())
            throw new InvalidTokenException("Refresh token used");
        if(refreshToken.getExpiresAt().isBefore(Instant.now()))
            throw new InvalidTokenException("Refresh token expired");

        return refreshToken;
    }

    @Transactional
    public void revokeToken(RefreshToken refreshToken){
        refreshToken.setRevoked(true);
        refreshToken.setRevokedAt(Instant.now());
    }

    @Transactional
    public void markAsUsed(RefreshToken refreshToken){
        refreshToken.setUsed(true);
    }

    @Transactional
    public void revokeAllUserTokens(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email [" + email + "] not found"));
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserAndRevokedFalse(user);
        for(RefreshToken token : tokens){
            token.setRevoked(true);
            token.setRevokedAt(Instant.now());
        }
    }

    private String generateSecureToken(){
        byte[] randomised = new byte[64];
        new SecureRandom().nextBytes(randomised);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomised);
    }
}
