package com.springbank.user.cmd.api.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
public class RemoveUserCommand {
    @TargetAggregateIdentifier
    private String id;
}
