package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
