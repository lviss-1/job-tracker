package com.luis.job_tracker.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "FrqeBUJ97MOEgf7Lqg2PpZz3qdY+ujBK5P44hgDgfxg=";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 900;
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 604800;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
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

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String email) {
        try {
            String extractedEmail = extractEmail(token);
            return extractedEmail.equals(email);
        } catch(JwtException e) {
            return false;
        }
    }
}
