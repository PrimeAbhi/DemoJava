package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.CartItem;
import com.demo.domain.TshirtToCartItem;

public interface TshirtToCartItemRepository extends CrudRepository<TshirtToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
