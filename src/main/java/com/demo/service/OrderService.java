package com.demo.service;

import com.demo.domain.BillingAddress;
import com.demo.domain.Order;
import com.demo.domain.Payment;
import com.demo.domain.ShippingAddress;
import com.demo.domain.ShoppingCart;
import com.demo.domain.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);

}
