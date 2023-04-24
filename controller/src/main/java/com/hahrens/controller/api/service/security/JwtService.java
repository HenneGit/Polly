package com.hahrens.controller.api.service.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * service for handling token based authentication.
 */
public interface JwtService {
    /**
     * extract username from token.
     * @param token the token to extract username from.
     * @return the username.
     */
    String extractUsername(String token);

    /**
     * extract claim from token by given function.
     * @param token the token to extract claim from.
     * @param claimsResolver the function that extracts the claim.
     * @param <T> the return of given function.
     * @return the extracted claim.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * generate a token from claims and user details.
     * @param extraClaims the claims to encrypt.
     * @param userDetails the user to details to encrypt.
     * @return the generated token.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * generate token from user details.
     * @param userDetails the user details to encrypt.
     * @return the generated token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * check if given token is valid.
     * @param token the token to check.
     * @param userDetails the user details to check the token for.
     * @return true if token is valid.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
