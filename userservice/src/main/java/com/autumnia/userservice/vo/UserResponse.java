package com.autumnia.userservice.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class UserResponse {
	private String email;
	private String name;
	private String userId;
	
	private List<OrderResponse> orders;
}
