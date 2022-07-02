package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.controllers.request.RegisterUserCommand;
import com.springbank.user.cmd.api.controllers.response.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/registerUser")
@Slf4j
public class RegisterUserController {
    private String SUCCESS_MESSAGE = "User successfully registered!";
    private String FAIL_MESSAGE = "Error while processing register user request for id - ";

    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway) {
        log.debug( "여기 1");
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand command) {
        log.debug( "여기 2");

        var id = UUID.randomUUID().toString();
        command.setId(id);

        log.debug( "id: {}", id);

        try {
            commandGateway.send(command);

            return new ResponseEntity<>(new RegisterUserResponse(id, SUCCESS_MESSAGE),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            log.error( e.toString() );
            var safeErrorMessage = FAIL_MESSAGE + id;

            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
