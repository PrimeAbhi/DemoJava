package com.demo.service;

import com.demo.domain.UserPayment;

public interface UserPaymentService {

	UserPayment findById(Long id);

	void removeById(Long id);

}
