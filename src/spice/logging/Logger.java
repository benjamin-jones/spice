package spice.logging;

public interface Logger {
    void postMessage(String message);
    void processLog();
    boolean hasMessages();
}

