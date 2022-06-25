package com.surfcode;

import static co.ke.lib.logs.Logging.ConfigLoggers;
import static co.ke.lib.logs.Logging.logger;
import com.surfcode.mikrotik.Commands;
import com.surfcode.models.Device;
import com.surfcode.models.Site;
import com.surfcode.services.DeviceService;
import com.surfcode.services.SiteService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import ke.co.lib.files.ConfigParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author dennis
 * 
 * App entry point
 */
@SpringBootApplication
public class Init{
    final static LinkedHashMap PROPS = ConfigParser.ReadProps("config");
    public final static String  
            DB_HOST = (String)PROPS.get("db_host"),
            DB_NAME = (String)PROPS.get("db_name"),
            DB_USER = (String)PROPS.get("db_user"),
            DB_PASS = (String)PROPS.get("db_pass"),
            APP_NAME = (String)PROPS.get("app");
    public final static int 
            BUCKET = Integer.valueOf((String)PROPS.get("bucket")),
            TIMER = Integer.valueOf((String)PROPS.get("timer"));    
    static{        
        ConfigLoggers((String) PROPS.get("log"),APP_NAME);
    }
    
    public static void main(String args[]){   
        SpringApplication.run(Init.class, args);
        List<Site> sites = new SiteService().findActive();
        try {
            ScheduledExecutorService executorTips = Executors.newScheduledThreadPool(sites.size());    
            for(Site site : sites){              
                ThreadRemoveUsers threadRemoveUsers = new ThreadRemoveUsers(site);
                logger.log(Level.INFO, "{0} : {1} (state) >>>>>>> started",new Object[]{APP_NAME,threadRemoveUsers.toString()} );
                executorTips.scheduleWithFixedDelay(threadRemoveUsers, 0, TIMER, TimeUnit.MINUTES);              
            }
        } catch (Exception ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, "{0}", ex);
        }
        
    }
}
