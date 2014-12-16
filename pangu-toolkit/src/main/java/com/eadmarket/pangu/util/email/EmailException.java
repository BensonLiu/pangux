package com.eadmarket.pangu.util.email;

/**
 * @author liuyongpo@gmail.com
 */
public class EmailException extends Exception {

  private static final long serialVersionUID = 3515267283248921681L;

  public EmailException() {
    super();
  }

  public EmailException(String message) {
    super(message);
  }

  public EmailException(String message, Throwable t) {
    super(message, t);
  }
}
