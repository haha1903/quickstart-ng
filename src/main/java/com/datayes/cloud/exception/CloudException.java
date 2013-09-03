package com.datayes.cloud.exception;

/**
 * User: changhai
 * Date: 13-9-2
 * Time: 下午1:05
 * DataYes
 */
public class CloudException extends Exception {
    public CloudException() {
    }

    public CloudException(String s) {
        super(s);
    }

    public CloudException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CloudException(Throwable throwable) {
        super(throwable);
    }
}
