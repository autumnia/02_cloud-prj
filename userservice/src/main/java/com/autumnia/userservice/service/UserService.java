package com.autumnia.userservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.autumnia.userservice.dto.UserDto;
import com.autumnia.userservice.repository.UserEntity;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto);
	UserDto findByUserId(String userId);
	Iterable<UserEntity> getUserByAll();
	UserDto getUserByEmail(String email);
}
