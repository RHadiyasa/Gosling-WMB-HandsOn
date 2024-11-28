package com.enigma.wmb_api.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.wmb_api.dto.response.JwtClaims;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final String JWT_SECRET;
    private final String JWT_ISSUER;
    private final long JWT_EXPIRATION;

    public JwtServiceImpl(
            @Value("${wmb_api.jwt.secret_key}") String jwtSecret,
            @Value("${wmb_api.jwt.issuer}") String jwtIssuer,
            @Value("${wmb_api.jwt.expirationInSecond}") long jwtExpiration
    ) {
        JWT_SECRET = jwtSecret;
        JWT_ISSUER = jwtIssuer;
        JWT_EXPIRATION = jwtExpiration;
    }

    @Override
    public String generateToken(UserAccount userAccount) {
        log.info("JWT_SECRET: {}", JWT_SECRET);
        log.info("JWT_ISSUER: {}", JWT_ISSUER);
        log.info("JWT_EXPIRATION: {}", JWT_EXPIRATION);

        try {
            // Algoritma untuk membuat JWT
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

            // claims pada jwt adalah informasi yang terkandung didalam token
            return JWT.create()
                    // Subject -> Untuk identifikasi user pada saat penggunaan token
                    // biasanya menggunakan ID dari user
                    .withSubject(userAccount.getId())
                    // Menetapkan waktu pembuatan token
                    .withIssuedAt(Instant.now())
                    // Menetapkan waktu expired token
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRATION))
                    // Menetapkan role
                    .withClaim("role", userAccount.getRole().getValue())
                    // si pembuat tokennya
                    .withIssuer(JWT_ISSUER)
                    // menandatangani token berdasarkan algoritma yang sudah dibuat
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();

            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            log.error("Invalid JWT : {}", exception.getMessage());
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            log.error("Invalid JWT Claims : {}", exception.getMessage());
            return null;
        }
    }
}
