package com.example.webfluxjwt.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService {

    final private SecretKey key;

    final private JwtParser parser;

    public JWTService() {
        key = Keys.hmacShaKeyFor("1234567890123456789012345678901234567890".getBytes());
        parser = Jwts.parser().setSigningKey(key).build();
    }

    public String generate(String username) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .signWith(key);

        return builder.compact();
    }

    public String getUserName(String token) {
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validate(UserDetails user, String token) {
        Claims claims = parser.parseEncryptedClaims(token).getBody();

        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));

        return unexpired && user.getUsername().equals(claims.getSubject());
    }
}
