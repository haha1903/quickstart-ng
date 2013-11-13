package com.datayes.cloud.exception;

/**
 * User: changhai
 * Date: 13-9-2
 * Time: 下午12:50
 * DataYes
 */
public class LoginException extends CloudException {
    public LoginException() {
    }

    public LoginException(String s) {
        super(s);
    }

    public LoginException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public LoginException(Throwable throwable) {
        super(throwable);
    }
}
