package spice.logging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import spice.events.EventThread;
import spice.routing.MessageRouter;
import spice.threading.SpiceThread;

public class LoggerThread extends Thread implements SpiceThread {

    private Logger currentLog;
    private boolean running;
    private boolean stopped = false;
    private final LoggerCallback loggerCallback;
    
    public BlockingQueue<String> queue;

    public LoggerThread() {
        MessageRouter router = MessageRouter.getInstance();
        queue = new LinkedBlockingQueue<>();
        loggerCallback = new LoggerCallback(queue);
        router.registerInterface("logger", loggerCallback);        
    }
    
    @Override
    public void shutdown() {
        running = false;
        
        while (!stopped) {
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(LoggerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (currentLog.hasMessages()) {
            currentLog.processLog();
        }
    }

    @Override
    public void run() {
        currentLog = new ConsoleLogger();

        running = true;

        while (running) {
            while (!queue.isEmpty())  {
                try {
                    currentLog.postMessage(queue.take());
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(EventThread.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }

            if (currentLog.hasMessages()) {
                currentLog.processLog();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(EventThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stopped = true;
    }

}
