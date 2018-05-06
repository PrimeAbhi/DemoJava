package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.UserBilling;

public interface UserBillingRepository extends CrudRepository<UserBilling, Long> {

}
