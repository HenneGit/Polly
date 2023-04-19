package com.hahrens.controller.implementation.registration;

import com.hahrens.storage.model.UserRole;

public record RegistrationRequest(String firstName, String lastName, String email,
                                  String password, UserRole userRole) {

}
