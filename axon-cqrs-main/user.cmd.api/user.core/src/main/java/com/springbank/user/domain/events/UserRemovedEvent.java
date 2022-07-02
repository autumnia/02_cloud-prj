package com.springbank.user.domain.events;

import lombok.Data;

@Data
public class UserRemovedEvent {
    private String id;
}
