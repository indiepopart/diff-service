package com.waes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="Invalid body")
public class DiffControllerInvalidBody extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DiffControllerInvalidBody(IllegalArgumentException e) {
		super(e);
	}

}
