package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.BillingAddress;

public interface BillingAddressRepository extends CrudRepository<BillingAddress, Long> {

}
