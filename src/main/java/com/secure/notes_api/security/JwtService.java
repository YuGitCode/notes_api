package com.secure.notes_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username)        // the "sub" claim — who this token is for
                .issuedAt(now)            // "iat" — when issued
                .expiration(expiry)       // "exp" — when it expires
                .signWith(signingKey)     // sign with the secret → the signature
                .compact();               // build the final header.payload.signature string
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);   // if this doesn't throw, the token is genuine and unexpired
            return true;
        } catch (JwtException e) {
            // signature invalid, expired, malformed — any failure means "not valid"
            return false;
        }
    }

    private  Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)   // check the signature using OUR secret key
                .build()
                .parseSignedClaims(token)
                .getPayload();            // the claims (subject, iat, exp)
    }
}