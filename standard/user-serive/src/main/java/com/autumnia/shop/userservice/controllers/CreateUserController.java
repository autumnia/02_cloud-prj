package com.autumnia.shop.userservice.controllers;

import com.autumnia.shop.userservice.common.exception.UserNotFoundException;
import com.autumnia.shop.userservice.repository.Post;
import com.autumnia.shop.userservice.repository.User;
import com.autumnia.shop.userservice.services.impl.PostServiceImpl;
import com.autumnia.shop.userservice.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CreateUserController {
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> user = userService.getUser(id);
        post.setUser( user.get() );
        Post savedPost = postService.createPost(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}