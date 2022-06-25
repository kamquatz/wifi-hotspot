package com.surfcode.mikrotik;

import static co.ke.lib.logs.Logging.logger;
import com.surfcode.Init;
import java.util.logging.Level;
import javax.net.SocketFactory;
import com.surfcode.mikrotik.core.ApiConnection;
import com.surfcode.mikrotik.core.ApiConnectionException;
import com.surfcode.mikrotik.core.MikrotikApiException;
import com.surfcode.models.Site;

/**
 *
 * @author Dennis
 */
 abstract class Base {
     protected ApiConnection con;
     
    public boolean connect(Site site){
         try {
             con = ApiConnection.connect(
                     SocketFactory.getDefault(),
                     site.getHost(),
                     site.getPort(),
                     2000
             );
             con.login(site.getUser(), site.getPass());
             return true;
         } catch (MikrotikApiException ex) {
             logger.log(Level.SEVERE, "{0}", ex.toString());
             return false;
         }
    }

    public void disconnect(){
        try {
            con.close();
        } catch (ApiConnectionException ex) {
            logger.log(Level.SEVERE, "{0}",  ex.toString());
        }
    }    
}
