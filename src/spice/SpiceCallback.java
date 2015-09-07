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

package spice;

import static java.lang.Thread.sleep;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import spice.events.EventThread;
import spice.routing.Callback;

/**
 *
 * @author benjamin-jones
 */
public class SpiceCallback implements Callback {
    
    private ConcurrentLinkedQueue<SpiceMessage> list = new ConcurrentLinkedQueue<SpiceMessage>();
    
    public boolean putMessage(Object object) {
        SpiceMessage msg = (SpiceMessage)object;
        
        list.add(msg);
        
        return true;
    }
    
    public Object getMessage() {
        
        while (list.isEmpty()) {
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(EventThread.class.getName()).log(Level.SEVERE, null, ex);
                continue;
            }
        }
        
        return list.remove();
    }
}
