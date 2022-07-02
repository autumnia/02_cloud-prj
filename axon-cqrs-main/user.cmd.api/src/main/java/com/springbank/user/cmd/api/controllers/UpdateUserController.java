package com.springbank.user.cmd.api.controllers;

import com.springbank.user.cmd.api.controllers.request.UpdateUserCommand;
import com.springbank.user.cmd.api.controllers.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/updateUser")
@Slf4j
public class UpdateUserController {
    private String SUCCESS_MESSAGE = "User successfully updated!";
    private String FAIL_MESSAGE = "Error while processing update user request for id - ";

    private final CommandGateway commandGateway;

    @Autowired
    public UpdateUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdateUserCommand command) {
        try {
            command.setId(id);
            commandGateway.send(command);

            return new ResponseEntity<>(new BaseResponse(SUCCESS_MESSAGE),
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            var errorMessage = FAIL_MESSAGE + id;

            return new ResponseEntity<>(new BaseResponse(errorMessage),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
