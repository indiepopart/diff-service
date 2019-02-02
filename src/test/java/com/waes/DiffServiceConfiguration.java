package com.waes;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;

import com.waes.model.BodyRepository;
import com.waes.service.DiffService;
import com.waes.service.DiffServiceImpl;


public class DiffServiceConfiguration {
	
	@Bean
	public BodyRepository bodyRepository() {
		return EasyMock.createMock(BodyRepository.class);
	}

	@Bean
	public DiffService diffService() {
		return new DiffServiceImpl();
	}
}
