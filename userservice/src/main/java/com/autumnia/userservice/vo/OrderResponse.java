package com.autumnia.userservice.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OrderResponse {
	private String orderId;
	
	private String productId;
	private Integer unitPrice;
	private Integer qty;
	private Integer totalPrice;
	private Date createdAt;
}
