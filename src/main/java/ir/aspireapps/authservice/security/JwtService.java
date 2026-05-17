package ir.aspireapps.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ir.aspireapps.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.access-token-expiration}")
    private long accessTokenExpirationMs;

    private SecretKey getSigningkey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user){
        Date now = new Date();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenExpirationMs))
                .signWith(getSigningkey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    public UUID extractUserId(String token){
        Object userId = extractAllClaims(token).get("userId");
        return userId != null ? UUID.fromString(userId.toString()) : null;
    }
    public String extractRole(String token){
        Object role = extractAllClaims(token).get("role");
        return role != null ? role.toString() : null;
    }
    public boolean isTokenValid(String token, String username){
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationMs / 1000;
    }
}

