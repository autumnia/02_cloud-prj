package com.springbank.user.cmd.api.controllers.request;

import com.springbank.user.domain.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull(message = "no user details were supplied")
    @Valid
    private User user;
}
