package com.autumnia.orderservice.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
	private String orderId;
	private String userId;
	
	private String productId;
//	private String produtName;
	private Integer qty;
	private Integer unitPrice;
	private Integer totalPrice;
	private Date createdAt;
}
