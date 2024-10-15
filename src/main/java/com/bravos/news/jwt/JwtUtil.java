package com.bravos.news.jwt;

import com.bravos.news.dto.UserInfo;
import com.bravos.news.entity.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class JwtUtil {

    private static final PrivateKey privateKey = KeyLoader.loadPrivateKey("D:\\Spring\\Servlet\\BravosNewsJDBC\\private.pem","16122005");
    private static final PublicKey publicKey = KeyLoader.loadPublicKey("D:\\Spring\\Servlet\\BravosNewsJDBC\\public.pem");

    public static String generateToken(UserInfo user, long tokenExp) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("sub", user.getUsername());
        claims.put("role",user.getRole());
        claims.put("created", new Date());
        return Jwts.builder()
                .claims()
                .add(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExp))
                .and()
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public static Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("Invalid token",e);
        }
    }

    public static  <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public static String extractUsername(String token) {
        try {
            return extractClaims(token,Claims::getSubject);
        } catch (Exception e) {
            return null;
        }
    }

    public static UserInfo extractUserInfoIfValid(String token) {
        String username = JwtUtil.extractUsername(token);
        UserInfo userInfo = null;
        if(JwtUtil.isValid(token,username)) {
            Claims claims = extractAllClaims(token);
            userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setId(UUID.fromString(claims.get("id", String.class)));
            userInfo.setRole(Role.valueOf(claims.get("role", String.class)));
        }
        return userInfo;
    }

    public static boolean isTokenExp(String token) {
        try {
            return extractClaims(token,Claims::getExpiration).before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValid(String token, String username) {
        try {
            if(token == null || username == null || token.isBlank() || username.isBlank()) {
                return false;
            }
            Claims claims = extractAllClaims(token);
            Date exp = claims.getExpiration();
            String tokenName = claims.getSubject();
            return exp.after(new Date()) && tokenName.equals(username);
        } catch (Exception e) {
            return false;
        }
    }

}
