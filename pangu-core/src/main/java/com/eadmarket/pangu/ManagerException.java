package com.eadmarket.pangu;

import lombok.Getter;

/**
 * Manager layer exception
 * 
 * @author liuyongpo@gmail.com
 */
public class ManagerException extends Exception {
	
	@Getter private final ExceptionCode code;
	
	public ManagerException(ExceptionCode code) {
		super();
		this.code = code;
	}
	
	public ManagerException(ExceptionCode code, Throwable t) {
		super(t);
		this.code = code;
	}
	
	public ManagerException(ExceptionCode code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}
	
	public boolean isSystemException() {
		return code.isSystemError();
	}
	
	private static final long serialVersionUID = 1L;
}
