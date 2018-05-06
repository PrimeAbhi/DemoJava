package com.demo.service;

import java.util.List;

import com.demo.domain.TShirt;

public interface TShirtService {

	List<TShirt> findAll();

	TShirt findOne(Long id);

	TShirt save(TShirt tshirt);

	List<TShirt> blurrySearch(String title);

	void removeOne(Long id);
}
