package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.UserShipping;
import com.demo.repository.UserShippingRepository;
import com.demo.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

	@Autowired
	private UserShippingRepository userShippingRepository;

	public UserShipping findById(Long id) {
		return userShippingRepository.findOne(id);
	}

	public void removeById(Long id) {
		userShippingRepository.delete(id);
	}
}
