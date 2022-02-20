package com.example.demo.service;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Value("${JWT_DURATION}")
    private long jwtDuration;

    @Value("${JWT_ISSUER}")
    private String jwtIssuer;

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return algorithm;
    }

    private DecodedJWT decode(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT;
    }

    public String generateAccessToken(Integer userId) {
        Date issueDate = new Date();
        Date expiredDate = new Date(issueDate.getTime() + jwtDuration);

        String token = JWT.create()
                .withIssuer(jwtIssuer)
                .withClaim("userid", userId)
                .withSubject("accessToken")
                .withIssuedAt(issueDate)
                .withExpiresAt(expiredDate)
                .sign(this.getAlgorithm());

        return token;
    }

    public Boolean verifyToken(String token) {
        JWTVerifier verifier = JWT.require(this.getAlgorithm()).withIssuer(jwtIssuer).build();

        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public Integer getUserIdFromAccessToken(String token) {
        DecodedJWT decodedJWT = this.decode(token);
        return decodedJWT.getClaim("userid").asInt();
    }

}
