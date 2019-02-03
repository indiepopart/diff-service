package com.waes.service;

import com.waes.model.Body;

public interface DiffService {

	/**
	 * Calculates the difference between to saved binary bodies
	 * @param leftId
	 * @param rightId
	 * @return DiffResult if equal size, different size, or equal size with differences
	 * @throws DiffServiceException if the difference cannot be calculated
	 */
	public DiffResult getDiff(Long leftId, Long rightId) throws DiffServiceException;
	
	public void saveBody(Body body);
}
