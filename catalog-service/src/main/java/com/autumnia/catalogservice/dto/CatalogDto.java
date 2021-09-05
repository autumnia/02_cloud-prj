package com.autumnia.catalogservice.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CatalogDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String orderId;
	
	private String productId;
	private Integer qty;
	private Integer unitPrice;
	private Integer totalPrice;
}
