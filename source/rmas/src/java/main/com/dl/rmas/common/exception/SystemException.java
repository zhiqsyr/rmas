package com.dl.rmas.common.exception;

@SuppressWarnings("serial")
public class SystemException extends RuntimeException {
	
	public SystemException() {
		super("system exception");
	}
	
	public SystemException(String message) {
		super(message);
	}
	
}
