package com.hahrens.controller.api.service.security;

import com.hahrens.controller.implementation.registration.RegistrationRequest;
import com.hahrens.controller.implementation.registration.validation.TokenValidationResult;
import com.hahrens.storage.model.User;
import com.hahrens.storage.model.VerificationToken;

/**
 * Service for registering a user.
 */
public interface RegistrationService {
    /**
     * register a user.
     * @param registrationRequest details of the user to register.
     * @return the created user.
     */
    User registerUser(RegistrationRequest registrationRequest);

    /**
     * save the token for completing registration.
     * @param user the user to create the given token for.
     * @param token the token for registration completion.
     */
    void saveUserVerificationToken(User user, String token);

    /**
     * find the verification  token from a given string.
     * @param token the string token to find verification token for.
     * @return the found verification token.
     */
    VerificationToken findByToken(String token);

    /**
     * validate a given token.
     * @param token the token to validate.
     * @return result of the validation.
     */
    TokenValidationResult validateToken(String token);

}
