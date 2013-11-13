package com.datayes.cloud.exception;

public class DatayesException extends Exception {
    public DatayesException() {
    }

    public DatayesException(String s) {
        super(s);
    }

    public DatayesException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DatayesException(Throwable throwable) {
        super(throwable);
    }
}
