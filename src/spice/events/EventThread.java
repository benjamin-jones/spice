/*
 * Copyright 2015 benjamin-jones.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spice.events;
import spice.routing.MessageRouter;
import spice.threading.SpiceThread;
import spice.SpiceMessage;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author benjamin-jones
 */
public class EventThread extends Thread implements SpiceThread {
    
    private boolean running = true;
    
    public void run() {
        MessageRouter router = MessageRouter.getInstance();
        while (running) {
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EventThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            router.notifyInterface("logger", "EventThread is sending a message...");
            router.notifyInterface("root", SpiceMessage.SHUTDOWN);
            
        }
       
    }
    
    public void shutdown() {
        running = false;
    }
}
