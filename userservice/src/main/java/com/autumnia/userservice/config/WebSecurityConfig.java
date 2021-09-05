package com.autumnia.userservice.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autumnia.userservice.filter.AuthenticationFilter;
import com.autumnia.userservice.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Environment env;
	private final UserService userService;
	private final BCryptPasswordEncoder	bCryptPasswordEncoder;
	
//	@Autowired
//	public WebSecurityConfig(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		this.env = env;
//		this.userService = userService;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}

	// 인증 작업
	// select pwd users where email = ?
	// db_pwd( encrypted ) == input_pwd (encrypted ) 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}	
	
	// 권한 작업
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/actuator/**").permitAll();
		
		http.authorizeRequests()
			.antMatchers("/**")
			.hasIpAddress( "127.0.0.1" )
			.and()
			.addFilter(getAuthenticationFilter());
		
		http.headers().frameOptions().disable();  //  프레임 사용 허가		
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), env, userService);
//		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	
}
