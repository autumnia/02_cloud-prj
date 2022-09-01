package com.autumnia.shop.userservice.controllers;

import com.autumnia.exception.UserNotFoundException;
import com.autumnia.shop.userservice.common.exception.PostNotFoundException;
import com.autumnia.shop.userservice.repository.Post;
import com.autumnia.shop.userservice.repository.User;
import com.autumnia.shop.userservice.services.impl.PostServiceImpl;
import com.autumnia.shop.userservice.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class DeleteUserController {
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }


    @DeleteMapping("/users/{id}/posts/{post_id}")
    public void deletePost(@PathVariable int id, @PathVariable int post_id) {
        Post post = postService.getPostById( post_id );
        postService.deletePost(post_id);
    }
}
