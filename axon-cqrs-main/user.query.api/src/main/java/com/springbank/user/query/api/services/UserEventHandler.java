package com.springbank.user.query.api.services;

import com.springbank.user.domain.events.UserRegisteredEvent;
import com.springbank.user.domain.events.UserRemovedEvent;
import com.springbank.user.domain.events.UserUpdatedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);
    void on(UserUpdatedEvent event);
    void on(UserRemovedEvent event);
}
