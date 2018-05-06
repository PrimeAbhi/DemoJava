package com.demo.service;

import com.demo.domain.UserShipping;

public interface UserShippingService {

	UserShipping findById(Long id);

	void removeById(Long id);
}
