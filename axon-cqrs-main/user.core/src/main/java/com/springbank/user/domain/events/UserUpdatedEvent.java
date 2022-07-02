package com.springbank.user.domain.events;

import com.springbank.user.domain.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdatedEvent {
    private String id;
    private User user;
}
