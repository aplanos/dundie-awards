package com.ninjaone.dundieawards.common.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String CLAIM_TENANT = "tenant";
    private final SecretKey secretKey;

    JwtTokenProvider(SecureParameterService secureParameterService) {
        this.secretKey = Keys.hmacShaKeyFor(
                secureParameterService.getParameter("jwt.secretKey")
                        .getBytes(StandardCharsets.UTF_8)
        );
    }    

    public String createToken(Authentication authentication) {

        final var expiryInstant = Instant.now().plus(Duration.ofHours(1));
        final var userDetails = (UserDetails) authentication.getPrincipal();
        final var username = userDetails.getUsername();

        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, username);
        claims.put(CLAIM_TENANT, "public");

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expiryInstant))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Check if the token is valid and not expired
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token ");
        }
        return false;
    }

    // Extract the username from the JWT token
    public String getUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract the username from the JWT token
    public String getUserDomain(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .get(CLAIM_TENANT).toString();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(
                        "employee_manage",
                        "organization_manage",
                        "activity_manage"
                )
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
