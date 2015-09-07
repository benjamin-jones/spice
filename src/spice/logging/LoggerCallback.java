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

package spice.logging;

import java.util.Queue;
import spice.routing.Callback;

/**
 *
 * @author benjamin-jones
 */
public class LoggerCallback implements Callback {
    
    private Queue<String> queue;
    
    public LoggerCallback(Queue<String> msgQ) {
        queue = msgQ;
    }
    
    public boolean putMessage(Object object) {
        queue.add((String)object);
        return true;
    }
    
    public Object getMessage() {
        
        return queue.remove();
    }
    
}
