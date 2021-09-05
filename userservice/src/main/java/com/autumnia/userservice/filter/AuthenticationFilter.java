package com.autumnia.userservice.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.autumnia.userservice.dto.UserDto;
import com.autumnia.userservice.service.UserService;
import com.autumnia.userservice.vo.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	final Environment env;
	final UserService userService;

	public AuthenticationFilter( AuthenticationManager authenticationManager, Environment env, UserService userService) {
		super.setAuthenticationManager(authenticationManager);
		this.env = env;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			LoginRequest credential = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					credential.getEmail(), 	
					credential.getPwd(), 
					new ArrayList<>());
			Authentication authenticate = getAuthenticationManager().authenticate(token);
			return authenticate;

		} 
		catch (Exception e) {
			log.error(" AuthenticationFilter 에러 발생: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String email = ((User)authResult.getPrincipal()).getUsername();
//		log.debug( "username: {}", email  );
		
		UserDto userDto = this.userService.getUserByEmail(email);
		
		long currentTime = System.currentTimeMillis();
		long expiration_time = Long.parseLong( this.env.getProperty("token.expiration_time") );
		Date expiration_date = new Date( currentTime + expiration_time );
		
		log.debug( "env token : {}", this.env.getProperty("token.expiration_time")  );
		log.debug( "currentTime: {}", currentTime  );
		log.debug( "expiration_time: {}", expiration_time  );
		log.debug( "expiration_date: {}", expiration_date  );
		
		String token = Jwts.builder()
				.setSubject(userDto.getUserId())
				.setExpiration( expiration_date )
				.signWith(SignatureAlgorithm.HS512, this.env.getProperty("token.secret"))
				.compact();
		response.addHeader("tokent",  token);
		response.addHeader("userId", userDto.getUserId());
		
	}
	
	
}
