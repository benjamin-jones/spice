package spice.logging;

/**
 * Created by xooxies on 3/14/14.
 */
public interface Logger {
    void postMessage(String message);
    void processLog();
    boolean hasMessages();
}

