package mobiclick;

import static co.ke.lib.logs.Logging.logger;
import java.util.logging.Level;
import static main.Init.*;

/**
 *
 * @author dennis
 */
public class ThreadAddUsers extends Thread{    
    @Override
    public void run() { 
        logger.log(Level.INFO, " (0) {0} (state) >>>>>>> processing",new Object[]{APP_NAME,this.toString()});    
        
        RemoteDb db = new RemoteDb();
        db.addCustomers();
        
        logger.log(Level.INFO, " (0) {1} (state) >>>>>>> sleep : {2} secs",new Object[]{APP_NAME,this.toString(),TIMER});
    }
}
