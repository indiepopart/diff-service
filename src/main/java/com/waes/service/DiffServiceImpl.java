package com.waes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.model.Body;
import com.waes.model.BodyRepository;

@Service
public class DiffServiceImpl implements DiffService {

	@Autowired
	protected BodyRepository bodyRepository;
	
	@Override
	public DiffResult getDiff(Long leftId, Long rightId) {
		return new DiffResult();
	}

	@Override
	public void saveBody(Body body) {
		bodyRepository.save(body);
	}
}
