package mikrotik;

import static co.ke.lib.logs.Logging.logger;
import java.util.logging.Level;
import javax.net.SocketFactory;
import mikrotik.core.ApiConnection;
import main.Init;
import mikrotik.core.ApiConnectionException;
import mikrotik.core.MikrotikApiException;

/**
 *
 * @author Dennis
 */
 abstract class Base {
     protected ApiConnection con;
     
    public boolean connect(){
         try {
             con = ApiConnection.connect(
                     SocketFactory.getDefault(),
                     Init.MIKROTIK_HOST,
                     ApiConnection.DEFAULT_PORT,
                     2000
             );
             con.login(Init.MIKROTIK_USER, Init.MIKROTIK_PASSWORD);
             return true;
         } catch (MikrotikApiException ex) {
             logger.log(Level.SEVERE, "{0}", ex);
             return false;
         }
    }

    public void disconnect(){
        try {
            con.close();
        } catch (ApiConnectionException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
    }
    
    
    
}
