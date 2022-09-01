package com.autumnia.shop.userservice.services;


import com.autumnia.shop.userservice.repository.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> getAllUsers() ;
    public Optional<User> getUser(int id);
    public User createUser(User user);
    public User updateUser(User user, int id);
    public void deleteUser(int id) ;
}
