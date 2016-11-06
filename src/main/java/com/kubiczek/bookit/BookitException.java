package com.kubiczek.bookit;

/**
 * Created by mkubiczek on 11/6/2016.
 */
public class BookitException extends RuntimeException {
    public BookitException(String message) {
        super(message);
    }

    public BookitException(String message, Exception e) {
        super(message, e);
    }
}
