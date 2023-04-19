package com.hahrens.controller.implementation.registration.event;

import com.hahrens.storage.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationURL;

    public RegistrationCompleteEvent(final User user ,final Object source) {
        super(source);
        this.user = user;
    }
}
