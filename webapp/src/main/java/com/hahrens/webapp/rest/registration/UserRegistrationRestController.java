package com.hahrens.webapp.rest.registration;

import com.hahrens.controller.api.registration.RegistrationService;
import com.hahrens.controller.implementation.registration.RegistrationRequest;
import com.hahrens.controller.implementation.registration.event.RegistrationCompleteEvent;
import com.hahrens.controller.implementation.registration.validation.TokenValidationResult;
import com.hahrens.storage.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user/registration")
@AllArgsConstructor
public class UserRegistrationRestController {

    private static final String REGISTER_SUCCESS = "Success! Please verify your email now.";
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher publisher;


    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request) {
        User user = registrationService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return REGISTER_SUCCESS;
    }


    @GetMapping("/verifyEmail/{token}")
    public String verifyEmail(@PathVariable String token) {
        TokenValidationResult tokenValidationResult = registrationService.validateToken(token);
        return tokenValidationResult.getMessage();
    }

    private String applicationUrl(final HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }




}
