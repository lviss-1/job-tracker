package com.luis.job_tracker.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "KEY";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 900;
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 604800;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email) {
        Date currentTime = new Date();
        Date expiryTime = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000L);
        return Jwts.builder()
                .subject(email)
                .issuedAt(currentTime)
                .expiration(expiryTime)
                .signWith(getSigningKey())
                .compact();
    }
}
