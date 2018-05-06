package com.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.CartItem;
import com.demo.domain.ShoppingCart;
import com.demo.repository.ShoppingCartRepository;
import com.demo.service.CartItemService;
import com.demo.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		BigDecimal cartTotal = new BigDecimal(0);

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			if (cartItem.getTshirt().getInStockNumber() > 0) {
				cartItemService.updateCartItem(cartItem);
				cartTotal = cartTotal.add(cartItem.getSubtotal());
			}
		}

		shoppingCart.setGrandTotal(cartTotal);

		shoppingCartRepository.save(shoppingCart);

		return shoppingCart;
	}

	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemService.save(cartItem);
		}

		shoppingCart.setGrandTotal(new BigDecimal(0));

		shoppingCartRepository.save(shoppingCart);
	}

}
