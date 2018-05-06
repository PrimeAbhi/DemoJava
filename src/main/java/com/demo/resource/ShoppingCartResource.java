package com.demo.resource;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.CartItem;
import com.demo.domain.ShoppingCart;
import com.demo.domain.TShirt;
import com.demo.domain.User;
import com.demo.service.CartItemService;
import com.demo.service.ShoppingCartService;
import com.demo.service.TShirtService;
import com.demo.service.UserService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartResource {
	@Autowired
	private UserService userService;

	@Autowired
	private TShirtService tshirtService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping("/add")
	public ResponseEntity<String> addItem(@RequestBody HashMap<String, String> mapper, Principal principal) {
		String tshirtId = (String) mapper.get("tshirtId");
		String qty = (String) mapper.get("qty");

		User user = userService.findByUsername(principal.getName());
		TShirt tshirt = tshirtService.findOne(Long.parseLong(tshirtId));

		if (Integer.parseInt(qty) > tshirt.getInStockNumber()) {
			return new ResponseEntity<String>("Not Enough Stock!", HttpStatus.BAD_REQUEST);
		}

		CartItem cartItem = cartItemService.addTshirtToCartItem(tshirt, user, Integer.parseInt(qty));

		return new ResponseEntity<String>("tshirt Added Successfully!", HttpStatus.OK);
	}

	@RequestMapping("/getCartItemList")
	public List<CartItem> getCartItemList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		shoppingCartService.updateShoppingCart(shoppingCart);

		return cartItemList;
	}

	@RequestMapping("/getShoppingCart")
	public ShoppingCart getShoppingCart(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		shoppingCartService.updateShoppingCart(shoppingCart);

		return shoppingCart;
	}

	@RequestMapping("/removeItem")
	public ResponseEntity<String> removeItem(@RequestBody String id) {
		cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)));

		return new ResponseEntity<String>("Cart Item Removed Successfully!", HttpStatus.OK);
	}

	@RequestMapping("/updateCartItem")
	public ResponseEntity<String> updateCartItem(@RequestBody HashMap<String, String> mapper) {
		String cartItemId = mapper.get("cartItemId");
		String qty = mapper.get("qty");

		CartItem cartItem = cartItemService.findById(Long.parseLong(cartItemId));
		cartItem.setQty(Integer.parseInt(qty));

		cartItemService.updateCartItem(cartItem);

		return new ResponseEntity<String>("Cart Item Updated Successfully!", HttpStatus.OK);
	}

}