package com.autumnia.shop.userservice.controllers;

import com.autumnia.shop.userservice.common.exception.UserNotFoundException;
import com.autumnia.shop.userservice.repository.Post;
import com.autumnia.shop.userservice.repository.User;
import com.autumnia.shop.userservice.services.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ReadUserController {
    private final UserServiceImpl userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);

        if ( !user.isPresent() ) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }


    @GetMapping("/v2/users/{id}")
    public EntityModel<User> getUser2(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);

        if ( !user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        EntityModel model = EntityModel.of(user);
        WebMvcLinkBuilder builder = linkTo(methodOn(this.getClass()).getUser2(id));
        model.add(builder.withRel("one-user"));

        return model;
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getAllPostsByUser(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        return user.get().getPosts();
    }
}
