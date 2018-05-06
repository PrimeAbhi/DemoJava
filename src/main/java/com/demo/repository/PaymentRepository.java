package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
