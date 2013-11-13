package com.datayes.cloud.util;

import java.io.IOException;

/**
 * User: changhai
 * Date: 13-8-15
 * Time: 上午8:43
 * DataYes
 */
public class DeleteUtil {
    private static final int MAX_WAIT = 20;
    private static final long INTERVAL = 1000;

    public static <T> void waitStatus(StatusHandler<T> handler, StatusChecker<T> checker) throws IOException {
        try {
            boolean done = false;
            for (int i = 0; i < MAX_WAIT; i++) {
                T status = handler.getStatus();
                if (checker.checkStatus(status)) {
                    done = true;
                    break;
                }
                Thread.sleep(INTERVAL);
            }
            if (!done)
                throw new IOException("wait status failure");
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public interface StatusHandler<T> {
        T getStatus() throws IOException;
    }

    public interface StatusChecker<T> {
        boolean checkStatus(T t) throws IOException;
    }
}
