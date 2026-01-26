package com.resona.global.oAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private static final String MEMBER_ID = "memberId";

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access-token-expiration}")
  private long accessTokenExpiration;

  @Value("${jwt.refresh-token-expiration}")
  private long refreshTokenExpiration;

  private Key key;

  @PostConstruct
  private void init() {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String createAccessToken(Long memberId) {
    return createToken(memberId, accessTokenExpiration);
  }

  public String createRefreshToken(Long memberId) {
    return createToken(memberId, refreshTokenExpiration);
  }

  private String createToken(Long memberId, long expiration) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + expiration);

    return Jwts.builder()
        .claim(MEMBER_ID, memberId)
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Long getMemberId(String token) {
    Claims claims = parseClaims(token);
    return claims.get(MEMBER_ID, Long.class);
  }

  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private Claims parseClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public Date getExpiration(String token) {
    Claims claims = parseClaims(token);
    return claims.getExpiration();
  }
}
