package com.autumnia.orderservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autumnia.orderservice.dto.OrderDto;
import com.autumnia.orderservice.repository.OrderEntity;
import com.autumnia.orderservice.repository.OrderRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
	
	final OrderRepository orderRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());
		
		OrderEntity orderEntity = new ModelMapper().map(orderDto, OrderEntity.class);

		this.orderRepository.save(orderEntity);

		OrderDto returnValue = new ModelMapper().map(orderEntity, OrderDto.class);

		return returnValue;
	}

	@Override
	public OrderDto getOrderByOrederId(String orderId) {
		OrderEntity aOrder = this.orderRepository.findByOrderId(orderId);
		OrderDto aDto = new ModelMapper().map(aOrder, OrderDto.class);
		return aDto; 
	}

	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return this.orderRepository.findByUserId(userId);
	}
	
	
}
