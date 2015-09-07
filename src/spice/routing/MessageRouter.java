package spice.routing;

import java.util.HashMap;

/**
 *
 * @author benjamin-jones
 */
public class MessageRouter {
    private static MessageRouter instance = null;
    private static HashMap<String,Callback> interfaceMap = new HashMap<String,Callback>();
    
    protected MessageRouter() {
        
    }
    
    public static MessageRouter getInstance() {
        if(instance == null) {
            instance = new MessageRouter();
        }
        return instance;
    }
    
    public boolean registerInterface(String guid, Callback callback) {
        boolean status = false;
        
        if ( interfaceMap.containsKey(guid) != true ) {
            interfaceMap.put(guid, callback);
            status = true;
        }
        
        return status;
    }
    
    public boolean notifyInterface(String guid, Object message) {
        boolean status = false;
        
        if (interfaceMap.containsKey(guid)) {
            Callback interfaceCb = (Callback)interfaceMap.get(guid);
            interfaceCb.putMessage(message);
            status = true;
            
        }
        return status;
    }
    
}
