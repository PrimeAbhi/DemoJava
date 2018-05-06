package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
