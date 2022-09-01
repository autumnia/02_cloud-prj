package com.autumnia.shop.userservice.controllers;


import com.autumnia.shop.userservice.common.exception.UserNotFoundException;
import com.autumnia.shop.userservice.repository.User;
import com.autumnia.shop.userservice.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UpdateUserController {
    private final UserServiceImpl userService;

    @PutMapping("/users")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
        User updatedUser = userService.updateUser( user, id );
        return updatedUser;
    }
}