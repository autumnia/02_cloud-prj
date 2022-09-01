package com.autumnia.shop.userservice.services.impl;

import com.autumnia.shop.userservice.common.exception.UserNotFoundException;
import com.autumnia.shop.userservice.repository.IUserRepository;
import com.autumnia.shop.userservice.repository.User;
import com.autumnia.shop.userservice.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public Optional<User> getUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        return user;
    }

    @Override
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User updateUser(User user, @PathVariable int id)  {
        this.getUser(id);
        user.setId( id );
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @Override
    public void deleteUser(int id) {
        this.getUser(id);
        userRepository.deleteById(id);
    }
}
