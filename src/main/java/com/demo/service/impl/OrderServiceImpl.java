package com.demo.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.BillingAddress;
import com.demo.domain.CartItem;
import com.demo.domain.Order;
import com.demo.domain.Payment;
import com.demo.domain.ShippingAddress;
import com.demo.domain.ShoppingCart;
import com.demo.domain.TShirt;
import com.demo.domain.User;
import com.demo.repository.BillingAddressRepository;
import com.demo.repository.OrderRepository;
import com.demo.repository.PaymentRepository;
import com.demo.repository.ShippingAddressRepository;
import com.demo.service.CartItemService;
import com.demo.service.OrderService;
import com.demo.service.TShirtService;
import com.demo.utility.MailConstructor;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillingAddressRepository billingAddressRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private TShirtService tshirtService;

	@Autowired
	private MailConstructor mailConstructor;

	public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			TShirt tshirt = cartItem.getTshirt();
			cartItem.setOrder(order);
			tshirt.setInStockNumber(tshirt.getInStockNumber() - cartItem.getQty());
		}

		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);

		return order;
	}

	public Order findOne(Long id) {
		return orderRepository.findOne(id);
	}

}
