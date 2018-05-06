package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {

}
