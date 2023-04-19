package com.hahrens.controller.implementation.registration.listener;

import com.hahrens.controller.implementation.registration.event.RegistrationCompleteEvent;
import com.hahrens.controller.implementation.service.UserService;
import com.hahrens.storage.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        String url = event.getApplicationURL() + "/user/registration/verifyEmail/" + verificationToken;
        log.info("Click the link to complete your registration: {}", url);

    }
}
