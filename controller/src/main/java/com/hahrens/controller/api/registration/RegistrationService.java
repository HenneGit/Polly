package com.hahrens.controller.api.registration;

import com.hahrens.controller.implementation.registration.RegistrationRequest;
import com.hahrens.storage.model.User;

public interface RegistrationService {

    User registerUser(RegistrationRequest registrationRequest);


    void saveUserVerificationToken(User user, String token);

}
