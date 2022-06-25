package main;

import static co.ke.lib.logs.Logging.ConfigLoggers;
import static co.ke.lib.logs.Logging.logger;
import java.util.LinkedHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import ke.co.lib.files.ConfigParser;
import mobiclick.ThreadAddUsers;
import mobiclick.ThreadRemoveUsers;

/**
 *
 * @author dennis
 * 
 * App entry point
 */
public class Init{
    final static LinkedHashMap PROPS = ConfigParser.ReadProps("config");
    public final static String             
            MIKROTIK_SITE = (String)PROPS.get("mikrotik_site"),
            MIKROTIK_HOST = (String)PROPS.get("mikrotik_host"),
            MIKROTIK_USER = (String)PROPS.get("mikrotik_user"),
            MIKROTIK_PASSWORD = (String)PROPS.get("mikrotik_password"),            
            MIKROTIK_PROFILE = (String)PROPS.get("mikrotik_profile"),             
            MIKROTIK_LIMIT_UPTIME = (String)PROPS.get("mikrotik_limit_uptime"), 
            DB_HOST = (String)PROPS.get("db_host"),
            DB_NAME = (String)PROPS.get("db_name"),
            DB_USER = (String)PROPS.get("db_user"),
            DB_PASS = (String)PROPS.get("db_pass"),
            CUSTOMER_GET_URL = (String)PROPS.get("get_customer_url"),
            CUSTOMER_UPDATE_URL = (String)PROPS.get("update_customer_url"),
            APP_NAME = (String)PROPS.get("app");
    public final static int MIKROTIK_PORT = Integer.valueOf((String)PROPS.get("mikrotik_port")),
            BUCKET = Integer.valueOf((String)PROPS.get("bucket")),
            TIMER = Integer.valueOf((String)PROPS.get("timer"));
    
    static{        
        ConfigLoggers((String) PROPS.get("log"),APP_NAME);
    }
    
    public static void main(String args[]){   
        try {
            ScheduledExecutorService executorTips = Executors.newScheduledThreadPool(2);
            
            ThreadAddUsers threadAddUsers = new ThreadAddUsers();            
            logger.log(Level.INFO, " (0) {1} (state) >>>>>>> started",new Object[]{APP_NAME,threadAddUsers.toString()} );
            executorTips.scheduleWithFixedDelay(threadAddUsers, 0, TIMER, TimeUnit.SECONDS);
            
            ThreadRemoveUsers threadRemoveUsers = new ThreadRemoveUsers();
            logger.log(Level.INFO, " (0) {1} (state) >>>>>>> started",new Object[]{APP_NAME,threadRemoveUsers.toString()} );
            executorTips.scheduleWithFixedDelay(threadRemoveUsers, 0, TIMER, TimeUnit.HOURS);  
            
        } catch (Exception ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, "{0}", ex);
        }
        
    }
}
