package com.eadmarket.pangu;

/**
 * @author liuyongpo@gmail.com
 */
public class DaoException extends Exception {
	
	public DaoException(Throwable t) {
		super(t);
	}
	
	public DaoException(String message, Throwable t) {
		super(message, t);
	}
	
	private static final long serialVersionUID = 1L;
}
