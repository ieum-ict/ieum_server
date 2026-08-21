package com.ieum.ict.ieum.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final String secret;

    public JwtTokenProvider(@Value("${jwt.secret:ieum-local-secret-key-must-be-at-least-32-bytes}") String secret) {
        this.secret = secret;
    }

    public Claims parse(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build().parseSignedClaims(token).getPayload();
    }

    public boolean isValid(String token) {
        try {
            Claims claims = parse(token);
            return claims.getExpiration() != null && claims.getExpiration().after(new Date());
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public String create(String subject, String type, long expirationMillis) {
        Date now = new Date();
        return Jwts.builder().subject(subject).claim("type", type).issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMillis))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).compact();
    }
}
