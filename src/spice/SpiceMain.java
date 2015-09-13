package spice;

import static java.lang.Thread.sleep;
import java.util.LinkedList;
import java.util.List;
import spice.events.EventThread;
import spice.logging.LoggerThread;
import spice.routing.MessageRouter;
import spice.threading.SpiceThread;


/**
 * Entry point of the application
 */
public class SpiceMain {

    private static LoggerThread loggerThread;
    private static EventThread eventThread;
    private static boolean running = true;
    private static final List<Thread> threadList = new LinkedList<>();
    private static Integer tockCount = 0;
    
    private static void handleMessage(SpiceMessage msg) {
        MessageRouter router = MessageRouter.getInstance();
        switch(msg) {
            case SHUTDOWN:
                running = false;
                break;
            case TOCK:
                router.notifyInterface("logger", "Tock event received!");
                tockCount++;
            default:
                break;
        }
        
    }
    
    private static void update() {
        MessageRouter router = MessageRouter.getInstance();
        
        if (tockCount > 10) {
            router.notifyInterface("logger", "Tock count exceeded maximum");
            router.notifyInterface("root", SpiceMessage.SHUTDOWN);
        }
        
    }

    public static void main(String[] args) throws InterruptedException {
        
        MessageRouter router = MessageRouter.getInstance();
        final SpiceCallback spiceCb = new SpiceCallback();
        
        router.registerInterface("root", spiceCb);
      
        loggerThread = new LoggerThread();
        eventThread = new EventThread();
        
        threadList.add(loggerThread);
        threadList.add(eventThread);
        
        for (Thread thread : threadList) {
            thread.start();
        }
        
        /* Wait for the logger interface to come online */
        while (!router.notifyInterface("logger","Spice is starting...")) {
            sleep(1);
        }
        
        router.notifyInterface("logger", "MessageRouter is started!");
        
        while (running) {
            SpiceMessage msg = (SpiceMessage)spiceCb.getMessage();
            handleMessage(msg);
            update();
        }
        
        router.notifyInterface("logger", "Spice is exiting...");
        
        for (Thread thread : threadList) {
            SpiceThread sThread = (SpiceThread)thread;
            sThread.shutdown();
            thread.join();
        }
    }
}
