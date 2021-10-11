package com.autumnia.jwtservice.controller;

import com.autumnia.jwtservice.dtos.UserDto;
import com.autumnia.jwtservice.entities.User;
import com.autumnia.jwtservice.jwts.JwtFilter;
import com.autumnia.jwtservice.services.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

//	@GetMapping("/hello")
//	public ResponseEntity<String> hello() {
//		return ResponseEntity.ok("hello");
//	}

	@PostMapping("/test-redirect")
	public void testRedirect(HttpServletResponse response) throws IOException {
		response.sendRedirect("/api/user");
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<User> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		log.debug("username: {}", username);
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}
}