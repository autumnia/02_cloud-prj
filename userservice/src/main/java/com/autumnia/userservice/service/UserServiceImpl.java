package com.autumnia.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.autumnia.userservice.dto.UserDto;
import com.autumnia.userservice.repository.UserEntity;
import com.autumnia.userservice.repository.UserRepository;
import com.autumnia.userservice.vo.OrderResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	
	
	@Override
	public UserDto createUser(UserDto userDto) {

		userDto.setUserId(UUID.randomUUID().toString());

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);

		userEntity.setEncryptedPwd(this.bCryptPasswordEncoder.encode(userDto.getPwd()));

		this.userRepository.save(userEntity);

		UserDto rtnUserDto = mapper.map(userEntity, UserDto.class);

		return rtnUserDto;
	}

	@Override
	public UserDto findByUserId(@PathVariable String userId) {
		UserEntity userEntity = this.userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(" 회원이 존재하지 않습니다. ");
		}

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userEntity, UserDto.class);

		List<OrderResponse> orders = new ArrayList<>();
		userDto.setOrders(orders);

		return userDto;
	}

	@Override
	public Iterable<UserEntity> getUserByAll() {
		Iterable<UserEntity> users = userRepository.findAll();
		return users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = this.userRepository.findByEmail(username);
		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		User user = new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), 
				true, true, true, true,
				new ArrayList<>());
		
		return user;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		UserEntity userEntity = this.userRepository.findByEmail(email);
		
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		return userDto;
	}
	

}
