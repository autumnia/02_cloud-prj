package com.autumnia.shop.userservice.services.impl;

import com.autumnia.exception.UserNotFoundException;
import com.autumnia.shop.userservice.common.exception.PostNotFoundException;
import com.autumnia.shop.userservice.repository.IPostRepository;
import com.autumnia.shop.userservice.repository.Post;
import com.autumnia.shop.userservice.repository.User;
import com.autumnia.shop.userservice.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);

        if (!post.isPresent()) {
            throw new PostNotFoundException(String.format("ID[%s} not found", id));
        }

        return post.get();
    }

    @Override
    public void deletePost(int id) {
        Post post = getPostById(id);
        postRepository.deleteById(id);
    }


}
