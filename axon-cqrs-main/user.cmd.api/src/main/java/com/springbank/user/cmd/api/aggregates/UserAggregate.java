package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.controllers.request.RegisterUserCommand;
import com.springbank.user.cmd.api.controllers.request.RemoveUserCommand;
import com.springbank.user.cmd.api.controllers.request.UpdateUserCommand;
import com.springbank.user.cmd.api.services.PasswordEncoder;
import com.springbank.user.cmd.api.services.PasswordEncoderImpl;
import com.springbank.user.domain.events.UserRegisteredEvent;
import com.springbank.user.domain.events.UserRemovedEvent;
import com.springbank.user.domain.events.UserUpdatedEvent;
import com.springbank.user.domain.models.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Aggregate
@Slf4j
public class UserAggregate {
    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAggregate() {
        log.debug( "여기1");
        passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        log.debug( "여기2");

        var newUser = command.getUser();
        newUser.setId(command.getId());

        log.debug( "newUser: {}", newUser);

        var password = newUser.getAccount().getPassword();
        passwordEncoder = new PasswordEncoderImpl();

        var hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event);
    }

//    @CommandHandler
//    public void handle(RegisterUserCommand command) {
//        var newUser = command.getUser();
//        newUser.setId(command.getId());
//
//        log.debug( "newUser: {}", newUser);
//
//
//        var password = newUser.getAccount().getPassword();
//        var passwordEncoder = new PasswordEncoderImpl();
//
//        var hashedPassword = passwordEncoder.hashPassword(password);
//        newUser.getAccount().setPassword(hashedPassword);
//
//        var event = UserRegisteredEvent.builder()
//                .id(command.getId())
//                .user(newUser)
//                .build();
//
//        AggregateLifecycle.apply(event);
//    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        var updatedUser = command.getUser();
        updatedUser.setId(command.getId());

        var password = updatedUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        var event = new UserRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
