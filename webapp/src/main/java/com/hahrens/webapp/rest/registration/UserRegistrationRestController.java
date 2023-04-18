package com.hahrens.webapp.rest.registration;

import com.hahrens.controller.api.registration.RegistrationService;
import com.hahrens.controller.implementation.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user/registration")
@AllArgsConstructor
public class UserRegistrationRestController {

    private final RegistrationService registrationService;

    public String register(@RequestBody RegistrationRequest registrationRequest) {
        return registrationService.register(registrationRequest);
    }

}
