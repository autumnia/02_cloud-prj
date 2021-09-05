package com.autumnia.userservice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autumnia.userservice.dto.UserDto;
import com.autumnia.userservice.repository.UserEntity;
import com.autumnia.userservice.service.UserService;
import com.autumnia.userservice.vo.UserRequest;
import com.autumnia.userservice.vo.UserResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UserController {
	private final UserService userService;
	private final Environment env;

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
			
		UserDto userDto = this.userService.findByUserId(userId);

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		
		UserResponse userResponse = mapper.map(userDto, UserResponse.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(userResponse) ;
	}		
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getUsers() {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		 Iterable<UserEntity> users = this.userService.getUserByAll();
		
		 List<UserResponse> result = new ArrayList<>();
		 users.forEach( v -> {
			 result.add( mapper.map(v, UserResponse.class) );
		 });
	 
		return ResponseEntity.status(HttpStatus.OK).body( result ) ;
	}	
	
	@PostMapping("/users")
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userRequest, UserDto.class);
		
		userDto = this.userService.createUser(userDto);
		
		UserResponse userResponse = mapper.map(userDto, UserResponse.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponse) ;
	}


	@GetMapping("/status")
	public String status(HttpServletRequest request) {
		
		log.info("유저서비스는 정상작동 중 입니다. ");
		log.info("현재시간: {}", this.getCurrentTime() );
		log.info("로컬서버포트: {}", this.env.getProperty("local.server.port") );
		log.info("원격서버포트: {}", this.env.getProperty("server.port") );
		log.info("토큰 시크릿: {}", this.env.getProperty("token.secret") );
		log.info("토큰 타임: {}", this.env.getProperty("token.expiration_time") );
		
		return "유저 서비스는 정상작동 중 입니다 " + this.env.getProperty("token.secret") ;
	}

	public String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String current_time = sdf.format( time.getTime() );
		return current_time;
	}
}
