package com.hahrens.controller.implementation.registration.validation;

/**
 * organize token validation result.
 */
public enum TokenValidationResult {

    USER_ENABLED("User is already enabled"), EXPIRED("Your token is expired."), VALID("Success! You may now login to your account!");

    private final String message;

    TokenValidationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
