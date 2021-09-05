package com.autumnia.orderservice.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrderRequest {
	@NotBlank(message="이름은 비어 있을 수 없습니다.")
	@Size(message="이름은 최소 2자 이상입니다.")		
	private String produtName;
	
	@NotBlank(message="수량은 비어 있을 수 없습니다.")
	private Integer qty;
	
	@NotBlank(message="수량은 비어 있을 수 없습니다.")
	@PositiveOrZero
	private Integer unitPrice;
	
	@NotBlank(message="수량은 비어 있을 수 없습니다.")
	@PositiveOrZero
	private Integer totalPrice;	
}
