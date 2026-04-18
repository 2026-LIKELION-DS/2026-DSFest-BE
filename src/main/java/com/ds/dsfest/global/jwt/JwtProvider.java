package com.ds.dsfest.global.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.ds.dsfest.domain.admin.exception.AdminErrorCode;
import com.ds.dsfest.global.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000L;

  private final JwtProperties jwtProperties;

  public String generateToken(String subject) {
    Date now = new Date();
    return Jwts.builder()
        .subject(subject)
        .issuedAt(now)
        .expiration(new Date(now.getTime() + EXPIRATION_MS))
        .signWith(secretKey())
        .compact();
  }

  public String extractSubject(String token) {
    return parseClaims(token).getSubject();
  }

  public void validate(String token) {
    parseClaims(token);
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException e) {
      throw new CustomException(AdminErrorCode.ADMIN_TOKEN_EXPIRED);
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException(AdminErrorCode.ADMIN_TOKEN_INVALID);
    }
  }

  private SecretKey secretKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
  }
}
