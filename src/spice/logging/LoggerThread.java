package spice.logging;

import java.util.concurrent.BlockingQueue;


/**
 * Created by xooxies on 3/14/14.
 */
public class LoggerThread extends Thread {

    private Logger currentLog;
    private boolean running;
    public BlockingQueue<String> queue;

    public LoggerThread(BlockingQueue<String>q) {
        queue = q;
    }

    public void run() {
        currentLog = new ConsoleLogger();

        running = true;

        while (running) {
            while (!queue.isEmpty()) {
                try {
                    currentLog.postMessage(queue.take());
                } catch (InterruptedException ex) {
                    break;
                }
            }

            if (currentLog.hasMessages()) {
                currentLog.processLog();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

}
