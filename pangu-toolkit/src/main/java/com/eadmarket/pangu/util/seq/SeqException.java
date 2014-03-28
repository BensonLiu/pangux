package com.eadmarket.pangu.util.seq;

/**
 * Created by liu on 3/28/14.
 */
public class SeqException extends Exception {

    public SeqException (String message) {
        super(message);
    }

    public SeqException (String message, Throwable cause) {
        super(message, cause);
    }
}
