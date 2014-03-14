package spice;

import spice.logging.LoggerThread;
import spice.logging.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xooxies on 3/14/14.
 */
public class SpiceMain {

    private static LoggerThread loggerThread;

    public static void main(String[] args) {

        BlockingQueue<String> msgq = new LinkedBlockingQueue<String>();
        loggerThread = new LoggerThread(msgq);
        loggerThread.start();
        try {
            msgq.put("Hi");

        } catch (InterruptedException ex) {

        }

    }
}
