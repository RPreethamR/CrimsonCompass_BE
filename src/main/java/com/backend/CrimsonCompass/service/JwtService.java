package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.model.User;
import com.backend.CrimsonCompass.security.JwtProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey jwtSecretKey;
    private final JwtProperties jwtProperties;

    public JwtService(SecretKey jwtSecretKey, JwtProperties jwtProperties) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(User user) {
        long ttl = jwtProperties.getExpiration();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ttl))
                .signWith(jwtSecretKey)
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
