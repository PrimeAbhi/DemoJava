package com.demo.resource;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.config.SecurityConfig;
import com.demo.config.SecurityUtility;
import com.demo.domain.User;
import com.demo.domain.security.Role;
import com.demo.domain.security.UserRole;
import com.demo.service.UserService;
import com.demo.utility.MailConstructor;

@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/newUser")
	public ResponseEntity newUserPost(HttpServletRequest request, @RequestBody HashMap<String, String> mapper)
			throws Exception {

		String username = mapper.get("username");
		String userEmail = mapper.get("email");

		if (userService.findByUsername(username) != null) {
			return new ResponseEntity("usernameExists", HttpStatus.BAD_REQUEST);
		}

		if (userService.findByEmail(userEmail) != null) {
			return new ResponseEntity("mailExists", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		SimpleMailMessage email = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(email);

		return new ResponseEntity("User Added Successfully!", HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "/forgetPassword")
	public ResponseEntity forgetPasswordPost(HttpServletRequest request, @RequestBody HashMap<String, String> mapper)
			throws Exception {

		User user = userService.findByEmail(mapper.get("email"));

		if (user == null) {
			return new ResponseEntity("Email not found", HttpStatus.BAD_REQUEST);
		}
		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		userService.save(user);

		SimpleMailMessage newEmail = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(newEmail);

		return new ResponseEntity("Email sent!", HttpStatus.OK);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/updateUserInfo")
	public ResponseEntity profileInfo(@RequestBody HashMap<String, Object> mapper) throws Exception {

		int id = (Integer) mapper.get("id");
		String email = (String) mapper.get("email");
		String username = (String) mapper.get("username");
		String firstName = (String) mapper.get("firstName");
		String lastName = (String) mapper.get("lastName");
		String newPassword = (String) mapper.get("newPassword");
		String currentPassword = (String) mapper.get("currentPassword");

		User currentUser = userService.findById(Long.valueOf(id));

		if (currentUser == null) {
			throw new Exception("User not found");
		}

		if (userService.findByEmail(email) != null) {
			if (userService.findByEmail(email).getId() != currentUser.getId()) {
				return new ResponseEntity("Email not found!", HttpStatus.BAD_REQUEST);
			}
		}

		if (userService.findByUsername(username) != null) {
			if (userService.findByUsername(username).getId() != currentUser.getId()) {
				return new ResponseEntity("Username not found!", HttpStatus.BAD_REQUEST);
			}
		}

		SecurityConfig securityConfig = new SecurityConfig();

		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		String dbPassword = currentUser.getPassword();

		if (null != currentPassword)
			if (passwordEncoder.matches(currentPassword, dbPassword)) {
				if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
					currentUser.setPassword(passwordEncoder.encode(newPassword));
				}
				currentUser.setEmail(email);
			} else {
				return new ResponseEntity("Incorrect current password!", HttpStatus.BAD_REQUEST);
			}

		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setUsername(username);

		userService.save(currentUser);

		return new ResponseEntity("Update Success", HttpStatus.OK);
	}

	@RequestMapping("/getCurrentUser")
	public User getCurrentUser(Principal principal) {
		User user = new User();
		if (null != principal) {
			user = userService.findByUsername(principal.getName());
		}

		return user;
	}

}