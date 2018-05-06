package com.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.CartItem;
import com.demo.domain.ShoppingCart;
import com.demo.domain.TShirt;
import com.demo.domain.TshirtToCartItem;
import com.demo.domain.User;
import com.demo.repository.CartItemRepository;
import com.demo.repository.TshirtToCartItemRepository;
import com.demo.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private TshirtToCartItemRepository tshirtToCartItemRepository;

	public CartItem addTshirtToCartItem(TShirt tshirt, User user, int qty) {
		// TODO Auto-generated method stub
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

		for (CartItem cartItem : cartItemList) {
			if (tshirt.getId() == cartItem.getTshirt().getId()) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubtotal(new BigDecimal(tshirt.getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}

		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setTshirt(tshirt);
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(tshirt.getOurPrice()).multiply(new BigDecimal(qty)));

		cartItem = cartItemRepository.save(cartItem);

		TshirtToCartItem tshirtToCartItem = new TshirtToCartItem();
		tshirtToCartItem.setTshirt(tshirt);
		tshirtToCartItem.setCartItem(cartItem);
		tshirtToCartItemRepository.save(tshirtToCartItem);

		return cartItem;
	}

	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	public CartItem updateCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		BigDecimal bigDecimal = new BigDecimal(cartItem.getTshirt().getOurPrice())
				.multiply(new BigDecimal(cartItem.getQty()));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);

		cartItemRepository.save(cartItem);

		return cartItem;
	}

	@Transactional
	public void removeCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		tshirtToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}

	public CartItem findById(Long id) {
		// TODO Auto-generated method stub
		return cartItemRepository.findOne(id);
	}

	public CartItem save(CartItem cartItem) {
		// TODO Auto-generated method stub
		return cartItemRepository.save(cartItem);
	}

}
