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
import com.demo.domain.UserShipping;
import com.demo.service.UserService;
import com.demo.service.UserShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingResource {
	@Autowired
	private UserService userService;

	@Autowired
	private UserShippingService userShippingService;

	@PostMapping("/add")
	public ResponseEntity<String> addNewUserShippingPost(@RequestBody UserShipping userShipping, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		userService.updateUserShipping(userShipping, user);

		return new ResponseEntity<String>("Shipping Added(Updated) Successfully!", HttpStatus.OK);
	}

	@RequestMapping("/getUserShippingList")
	public List<UserShipping> getUserShippingList(Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<UserShipping> userShippingList = user.getUserShippingList();

		return userShippingList;
	}

	@PostMapping("/remove")
	public ResponseEntity<String> removeUserShippingPost(@RequestBody String id, Principal principal) {
		userShippingService.removeById(Long.parseLong(id));
		return new ResponseEntity<String>("Shipping Removed Successfully!", HttpStatus.OK);
	}

	@PostMapping("/setDefault")
	public ResponseEntity<String> setDefaultUserShippingPost(@RequestBody String id, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultShipping(Long.parseLong(id), user);

		return new ResponseEntity<String>("Set Default Shipping Successfully!", HttpStatus.OK);
	}
}
