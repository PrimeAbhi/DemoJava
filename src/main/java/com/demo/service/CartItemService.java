package com.demo.service;

import java.util.List;

import com.demo.domain.CartItem;
import com.demo.domain.ShoppingCart;
import com.demo.domain.TShirt;
import com.demo.domain.User;

public interface CartItemService {
	CartItem addTshirtToCartItem(TShirt tshirt, User user, int qty);

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	// List<CartItem> findByOrder(Order order);

	CartItem updateCartItem(CartItem cartItem);

	void removeCartItem(CartItem cartItem);

	CartItem findById(Long id);

	CartItem save(CartItem cartItem);

}
