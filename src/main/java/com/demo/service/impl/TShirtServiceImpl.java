package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.TShirt;
import com.demo.repository.TShirtRepository;
import com.demo.service.TShirtService;

@Service
public class TShirtServiceImpl implements TShirtService {

	@Autowired
	private TShirtRepository tshirtRepository;

	public List<TShirt> findAll() {
		List<TShirt> tshirtList = (List<TShirt>) tshirtRepository.findAll();

		return tshirtList;
	}

	public TShirt findOne(Long id) {
		return tshirtRepository.findOne(id);
	}

	public TShirt save(TShirt book) {
		// TODO Auto-generated method stub
		return tshirtRepository.save(book);
	}

	public List<TShirt> blurrySearch(String keyword) {
		List<TShirt> tshirtList = tshirtRepository.findByTitleContaining(keyword);

		return tshirtList;
	}

	public void removeOne(Long id) {
		tshirtRepository.delete(id);
	}

}
