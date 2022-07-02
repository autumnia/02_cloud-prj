package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.controllers.request.RemoveUserCommand;
import com.springbank.user.cmd.api.controllers.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/removeUser")
@Slf4j
public class RemoveUserController {
    private String SUCCESS_MESSAGE = "User successfully removed!";
    private String FAIL_MESSAGE = "Error while processing remove user request for id - ";

    private final CommandGateway commandGateway;

    @Autowired
    public RemoveUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id) {
        try {
            commandGateway.send(new RemoveUserCommand(id));

            return new ResponseEntity<>(new BaseResponse(SUCCESS_MESSAGE),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error( e.toString() );
            var errorMessage = FAIL_MESSAGE + id;

            return new ResponseEntity<>(new BaseResponse(errorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
