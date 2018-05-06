package com.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.TShirt;

public interface TShirtRepository extends CrudRepository<TShirt, Long> {
	List<TShirt> findByTitleContaining(String keyword);

}
