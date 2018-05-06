package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.ShippingAddress;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long> {

}
