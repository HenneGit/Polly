package com.hahrens.controller.implementation.service.security;

import com.hahrens.controller.api.service.security.RegistrationService;
import com.hahrens.controller.implementation.registration.RegistrationRequest;
import com.hahrens.controller.implementation.registration.UserFactory;
import com.hahrens.controller.implementation.registration.exception.UserAlreadyExistsException;
import com.hahrens.controller.implementation.registration.validation.TokenValidationResult;
import com.hahrens.storage.model.User;
import com.hahrens.storage.model.VerificationToken;
import com.hahrens.storage.repository.UserEntityRepository;
import com.hahrens.storage.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final String USER_EXISTS_MSG = "User with email s% already exists.";
    private final UserEntityRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public User registerUser(final RegistrationRequest registrationRequest) {
        Optional<User> user = userRepository.findByEmail(registrationRequest.email());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException(String.format(USER_EXISTS_MSG, registrationRequest.email()));
        }
        return userRepository.save(UserFactory.getUserFromRequest(registrationRequest, passwordEncoder.encode(registrationRequest.password())));
    }

    @Override
    public void saveUserVerificationToken(final User user, final String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public TokenValidationResult validateToken(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        User user = token.getUser();
        if (user.isEnabled()) {
            return TokenValidationResult.USER_ENABLED;
        }
        if (token.getExpirationDate().before(new Date())) {
            verificationTokenRepository.delete(token);
            return TokenValidationResult.EXPIRED;
        }
        user.setEnabled(true);
        userRepository.save(user);
        return TokenValidationResult.VALID;
    }

}
