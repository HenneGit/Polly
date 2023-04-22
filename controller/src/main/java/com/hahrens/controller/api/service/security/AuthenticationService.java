package com.hahrens.controller.api.service.security;

import com.hahrens.controller.implementation.security.AuthenticationRequest;
import com.hahrens.controller.implementation.security.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
