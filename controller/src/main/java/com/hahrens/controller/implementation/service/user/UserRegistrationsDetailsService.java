package com.hahrens.controller.implementation.service.user;

import com.hahrens.controller.implementation.registration.model.UserRegistrationDetails;
import com.hahrens.storage.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationsDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userEntityRepository.findByEmail(email).map(UserRegistrationDetails::new).orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

}
