package com.demo.service;

import java.util.Set;

import com.demo.domain.User;
import com.demo.domain.UserBilling;
import com.demo.domain.UserPayment;
import com.demo.domain.UserShipping;
import com.demo.domain.security.UserRole;

public interface UserService {

	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User findByEmail(String email);

	User save(User user);

	User findById(Long valueOf);

	void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultShipping(Long userShippingId, User user);

}
