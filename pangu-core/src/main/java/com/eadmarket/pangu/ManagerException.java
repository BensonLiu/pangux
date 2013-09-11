package com.eadmarket.pangu;

import lombok.Getter;

/**
 * Manager layer exception
 * 
 * @author liuyongpo@gmail.com
 */
public class ManagerException extends Exception {
	
	@Getter private final int code;
	private final boolean isSystemException;
	
	public ManagerException(ExceptionCode code) {
		super();
		this.code = code.getCode();
		isSystemException = code.isSystemError();
	}
	
	public ManagerException(ExceptionCode code, Throwable t) {
		super(t);
		this.code = code.getCode();
		isSystemException = code.isSystemError();
	}
	
	public ManagerException(ExceptionCode code, String message, Throwable t) {
		super(message, t);
		this.code = code.getCode();
		isSystemException = code.isSystemError();
	}
	
	public boolean isSystemException() {
		return isSystemException;
	}
	
	private static final long serialVersionUID = 1L;
}
