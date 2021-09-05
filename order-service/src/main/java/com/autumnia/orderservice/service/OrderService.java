package com.autumnia.orderservice.service;

import com.autumnia.orderservice.dto.OrderDto;
import com.autumnia.orderservice.repository.OrderEntity;

public interface OrderService {
	OrderDto createOrder(OrderDto orderDto);
	OrderDto getOrderByOrederId(String orderId);
	Iterable<OrderEntity> getOrdersByUserId(String userId);
}
