package com.waes.service;

import com.waes.model.Body;

public interface DiffService {

	public DiffResult getDiff(Long left, Long right) throws DiffServiceException;
	
	public void saveBody(Body body);
}
