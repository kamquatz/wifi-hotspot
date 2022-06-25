package com.surfcode;

import static co.ke.lib.logs.Logging.logger;
import static com.surfcode.Init.*;
import com.surfcode.mikrotik.Commands;
import com.surfcode.models.Device;
import com.surfcode.models.Site;
import com.surfcode.services.DeviceService;
import com.surfcode.services.SiteService;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author dennis
 */
public class ThreadRemoveUsers extends Thread{  
    final Site site;
    public ThreadRemoveUsers(Site site){
        this.site = site;
    }
    @Override
    public void run() { 
        logger.log(Level.INFO, "{0} : {1} (state) >>>>>>> processing",new Object[]{APP_NAME,this.toString()});   
        
        Commands c = new Commands();
        c.connect(site);

        boolean isRemoved = false;            
        for(Device device : new DeviceService().findAllExpired(site)){
            device.setDevice(2);
            new DeviceService().update(device, site);
            Map<String, String> user = c.getUser(device.getPhone());
            if(user!=null)  c.removeUser(user.get(".id"));

            logger.log(Level.INFO, "removed hotspot user :phone={0}", device.getPhone());

        }
        c.disconnect();   
        
        logger.log(Level.INFO, "{0} : {1} (state) >>>>>>> sleep : {2} minutes",new Object[]{APP_NAME,this.toString(),TIMER});
    }
}
