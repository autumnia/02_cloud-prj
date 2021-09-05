package com.autumnia.orderservice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import com.autumnia.orderservice.dto.OrderDto;
import com.autumnia.orderservice.repository.OrderEntity;
import com.autumnia.orderservice.service.OrderService;
import com.autumnia.orderservice.vo.OrderRequest;
import com.autumnia.orderservice.vo.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("orderservice")
public class OrderController {
	final OrderService orderService;
	final Environment env;

	public OrderController(Environment env, OrderService orderService) {
		this.env = env;
		this.orderService = orderService;
	}

//	@GetMapping("/users/{userId}")
//	public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") String userId) {
//			
//		UserDto userDto = this.userService.findByUserId(userId);
//
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		
//		UserResponse userResponse = mapper.map(userDto, UserResponse.class);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(userResponse) ;
//	}		
	
	@GetMapping("/{userId}/users")
	public ResponseEntity<List<OrderResponse>> getOrder(@PathVariable("userId") String userId) {
		 Iterable<OrderEntity> orders = this.orderService.getOrdersByUserId(userId);
		
		 List<OrderResponse> result = new ArrayList<>();
		 orders.forEach( v -> {
			 result.add( new ModelMapper().map(v, OrderResponse.class) );
		 });
	 
		return ResponseEntity.status(HttpStatus.OK).body( result ) ;
	}	
	
	@PostMapping("/{userId}/orders")
	public ResponseEntity<OrderResponse> createOrder(@PathVariable("userId") String userId,
			@RequestBody OrderRequest orderRequest) {
	
		// request -> dto 변환
		OrderDto orderDto = new ModelMapper().map(orderRequest, OrderDto.class);
		orderDto.setUserid(userId);
		orderRequest.setTotalPrice(orderRequest.getUnitPrice()  * orderDto.getQty() );
		
		// dto 저장
		orderDto = this.orderService.createOrder(orderDto);
		
		// dto -> response 변환
		OrderResponse orderResponse = new ModelMapper().map(orderDto, OrderResponse.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse) ;
	}	
	
	@GetMapping("/status")
	public String status() {
		String message = String.format("%s order service가 정상 작동 중입니다.  port:%s",  
				this.getCurrentTime(), 
				this.env.getProperty("local.server.port"));
		log.info(message);
		
		return message;
	}

	public String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String current_time = sdf.format( time.getTime() );
		return current_time;
	}
}
