package com.hahrens.controller.implementation.registration;

public record RegistrationRequest(String firstName, String lastName, String email,
                                  String password) {

}
