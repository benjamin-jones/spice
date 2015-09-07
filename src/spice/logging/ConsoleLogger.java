package spice.logging;

import java.util.Queue;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsoleLogger implements Logger {

    private Queue<String> messageLog;

    public ConsoleLogger()
    {
        messageLog = new LinkedList<String>();
    }

    public void postMessage(String msg)
    {
        messageLog.add(msg);
    }

    public void processLog()
    {
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        for (String msg : messageLog)
        {
            System.out.println(sdf.format(cal.getTime()) + " " + msg);
            messageLog.remove();
        }
    }

    public boolean hasMessages() {
        if (messageLog.isEmpty()) {
            return false;
        }

        return true;
    }
}
