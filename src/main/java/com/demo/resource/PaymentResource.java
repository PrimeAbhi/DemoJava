package com.demo.resource;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.User;
import com.demo.domain.UserBilling;
import com.demo.domain.UserPayment;
import com.demo.service.UserPaymentService;
import com.demo.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentResource {

	@Autowired
	private UserService userService;

	@Autowired
	private UserPaymentService userPaymentService;

	@PostMapping("/add")
	public ResponseEntity<String> addNewCreditCardPost(@RequestBody UserPayment userPayment, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		UserBilling userBilling = userPayment.getUserBilling();

		userService.updateUserBilling(userBilling, userPayment, user);
		return new ResponseEntity<String>("Payment Added(Updated) Successfully!", HttpStatus.OK);
	}

	@PostMapping("/remove")
	public ResponseEntity<String> removePaymentPost(@RequestBody String id, Principal principal) {
		// User user = userService.findByUsername(principal.getName());
		userPaymentService.removeById(Long.valueOf(id));
		return new ResponseEntity<String>("Payment Removed Successfully!", HttpStatus.OK);
	}

	@PostMapping("/setDefault")
	public ResponseEntity<String> setDefaultPaymentPost(@RequestBody String id, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(Long.parseLong(id), user);
		return new ResponseEntity<String>("Payment Removed Successfully!", HttpStatus.OK);
	}

	@RequestMapping("/getUserPaymentList")
	public List<UserPayment> getUserPaymentList(Principal principal) {
		User user = userService.findByUsername(principal.getName());

		List<UserPayment> userPaymentList = user.getUserPaymentList();
		return userPaymentList;
	}

}
