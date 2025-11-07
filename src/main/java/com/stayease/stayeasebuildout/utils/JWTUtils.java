package com.stayease.stayeasebuildout.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

    private String secreyKey = "hdlssfla;df9i23ewld;ds;kfd[3455467dfhgfhjhjhkkll.gfbn,jkhhfgghrtrutkjyhkhj";
    private SecretKey key = Keys.hmacShaKeyFor(secreyKey.getBytes());
    private Long expiration = 1000 * 60 * 60L;

    public String generateToken(String email) {
        return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key)
        .compact();
    }

    public Claims extarctClaims(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims;
    }

    public String extractEmail(String token) {
        Claims claims = extarctClaims(token);
        return claims.getSubject();
    }

    public boolean validateToken(String email, UserDetails userDetails, String token) {
        if(email.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        Claims claims = extarctClaims(token);
        return claims.getExpiration().before(new Date());
    }
}
