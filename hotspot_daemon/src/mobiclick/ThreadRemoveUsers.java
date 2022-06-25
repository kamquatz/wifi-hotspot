package mobiclick;

import static co.ke.lib.logs.Logging.logger;
import java.util.Map;
import java.util.logging.Level;
import static main.Init.*;
import mikrotik.Commands;
import mikrotik.Users;

/**
 *
 * @author dennis
 */
public class ThreadRemoveUsers extends Thread{
    
    @Override
    public void run() { 
        logger.log(Level.INFO, " (0) {0} (state) >>>>>>> processing",new Object[]{APP_NAME,this.toString()});    
        
        Users u = new Users();
        boolean isRemoved = false;
        u.connect();   
        for (Map<String, String> user : u.getAll()){
            String limitUptime = user.get("limit-uptime")==null ? "" : user.get("limit-uptime");            
            if(user.get("uptime").equals(limitUptime) || user.get("uptime").equals("0s") ) 
                isRemoved = u.remove(user.get(".id"));
        }        
        u.disconnect();   
        
        logger.log(Level.INFO, " (0) {1} (state) >>>>>>> sleep : {2} hours",new Object[]{APP_NAME,this.toString(),TIMER});
    }
}
