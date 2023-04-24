package com.hahrens.controller.api.service.security;

import com.hahrens.controller.implementation.security.AuthenticationRequest;
import com.hahrens.controller.implementation.security.AuthenticationResponse;

/**
 * service for user authentication.
 */
public interface AuthenticationService {
    /**
     * authenticate the given request.
     * @param authenticationRequest the request to authenticate.
     * @return the token for user details.
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
