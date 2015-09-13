package spice.logging;

import java.util.Queue;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

public class ConsoleLogger implements Logger {

    private final Queue<String> messageLog;
    private final ReentrantLock lock = new ReentrantLock();
    public ConsoleLogger()
    {
        messageLog = new LinkedList<>();
    }

    @Override
    public void postMessage(String msg)
    {
        this.lock.lock();
        try {
            messageLog.add(msg);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void processLog()
    {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        this.lock.lock();
        try {
            for (String msg : messageLog)
            {
                System.out.println(sdf.format(cal.getTime()) + " " + msg);
                messageLog.remove();
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean hasMessages() {

        return !messageLog.isEmpty();
    }
}
