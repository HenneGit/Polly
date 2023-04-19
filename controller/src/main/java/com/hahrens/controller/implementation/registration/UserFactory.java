package com.hahrens.controller.implementation.registration;

import com.hahrens.storage.model.User;

public class UserFactory {


    private UserFactory() {
        //hide constructor
    }

    public static User getUserFromRequest(final RegistrationRequest registrationRequest, final String encodedPassword) {
        User user = new User();
        user.setUserRole(registrationRequest.userRole());
        user.setLastName(registrationRequest.lastName());
        user.setFirstName(registrationRequest.firstName());
        user.setEmail(registrationRequest.email());
        user.setPassword(encodedPassword);
        user.setUserName(registrationRequest.email());
        user.setEmail(registrationRequest.email());
        return user;
    }


}
