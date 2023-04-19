package com.hahrens.controller.implementation.registration;

import com.hahrens.controller.api.registration.RegistrationService;
import com.hahrens.controller.implementation.registration.exception.UserAlreadyExistsException;
import com.hahrens.storage.model.User;
import com.hahrens.storage.model.VerificationToken;
import com.hahrens.storage.repository.UserEntityRepository;
import com.hahrens.storage.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        User save = userRepository.save(UserFactory.getUserFromRequest(registrationRequest, passwordEncoder.encode(registrationRequest.password())));
        return save;
    }

    @Override
    public void saveUserVerificationToken(final User user, final String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        VerificationToken save = verificationTokenRepository.save(verificationToken);

    }

}
