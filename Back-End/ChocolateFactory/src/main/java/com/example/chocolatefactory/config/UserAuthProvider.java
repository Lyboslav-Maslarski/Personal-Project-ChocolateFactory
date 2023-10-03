package com.example.chocolatefactory.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.chocolatefactory.domain.dtos.UserDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthProvider {

    @Value("${secuurity.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTO userDTO) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(userDTO.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("fullName", userDTO.getFullName())
                .withClaim("city", userDTO.getCity())
                .withClaim("address", userDTO.getAddress())
                .withClaim("phone", userDTO.getPhone())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT verify = jwtVerifier.verify(token);

        UserDTO userDTO = new UserDTO()
                .setEmail(verify.getIssuer())
                .setFullName(verify.getClaim("fullName").asString())
                .setFullName(verify.getClaim("city").asString())
                .setFullName(verify.getClaim("address").asString())
                .setFullName(verify.getClaim("phone").asString());

        return new UsernamePasswordAuthenticationToken(userDTO, null, Collections.emptyList());
    }
}
