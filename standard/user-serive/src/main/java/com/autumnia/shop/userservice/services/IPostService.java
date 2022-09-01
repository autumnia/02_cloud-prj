package com.autumnia.shop.userservice.services;

import com.autumnia.shop.userservice.repository.Post;

public interface IPostService {
    public Post createPost(Post post) ;
    public void deletePost(int id);
    public Post getPostById(int id);
}
