package com.demo.service;

import com.demo.domain.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	void clearShoppingCart(ShoppingCart shoppingCart);
}
