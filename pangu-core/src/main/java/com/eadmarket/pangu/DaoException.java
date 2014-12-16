package com.eadmarket.pangu;

/**
 * @author liuyongpo@gmail.com
 */
public class DaoException extends Exception {

  private static final long serialVersionUID = 1L;

  public DaoException(Throwable t) {
    super(t);
  }

  public DaoException(String message, Throwable t) {
    super(message, t);
  }
}
