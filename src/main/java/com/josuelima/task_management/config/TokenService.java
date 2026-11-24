package com.josuelima.task_management.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.josuelima.task_management.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Component
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    @Value("${spring.application.name}")
    private String applicationName;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(applicationName)
                    .withSubject(user.getUsername())
                    .withExpiresAt(this.genExpireDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw  new RuntimeException("Token inválido ou expirado", exception);
        }
    }

    public String getExpireDateToken(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .toString();
    }

    private Instant genExpireDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
