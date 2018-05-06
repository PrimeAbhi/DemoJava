package com.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.Order;
import com.demo.domain.User;

public interface OrderRepository extends CrudRepository<Order, Long> {
	List<Order> findByUser(User user);

}
