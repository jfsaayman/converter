package com.jo.converter.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ConvertionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvertionException(String message) {
		super(message);
	}

}
