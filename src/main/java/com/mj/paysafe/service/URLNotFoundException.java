package com.mj.paysafe.service;

/**
 * @author Malay Patel
 *
 */
@SuppressWarnings("serial")
public class URLNotFoundException extends RuntimeException 
{
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	public URLNotFoundException(String exception) {
		super(exception);
	}

}

