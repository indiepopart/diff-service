package com.waes;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import com.waes.model.BodyRepository;
import com.waes.service.DiffService;
import com.waes.service.DiffServiceImpl;


public class DiffServiceConfiguration {
	
	@Bean
	public BodyRepository bodyRepository() {
		return Mockito.mock(BodyRepository.class);
	}

	@Bean
	public DiffService diffService() {
		return new DiffServiceImpl();
	}
}
