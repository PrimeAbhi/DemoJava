package com.demo.resource;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.UserService;

@RestController
public class LoginResource {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/token")
	public Map<String, String> token(HttpSession session, HttpServletRequest request) {
		System.out.println(request.getRemoteHost());

		String remoteHost = request.getRemoteHost();
		int portNumber = request.getRemotePort();

		System.out.println(remoteHost + ":" + portNumber);
		System.out.println(request.getRemoteAddr());

		return Collections.singletonMap("token", session.getId());
	}

	@RequestMapping("/checkSession")
	public ResponseEntity<String> checkSession() {
		return new ResponseEntity<String>("Session Active!", HttpStatus.OK);
	}

	@PostMapping("/user/logout")
	public ResponseEntity<String> logout() {
		SecurityContextHolder.clearContext();
		return new ResponseEntity<String>("Logout Successfully!", HttpStatus.OK);
	}
}
