package mikrotik;

import static co.ke.lib.logs.Logging.logger;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import static main.Init.*;
import mikrotik.core.MikrotikApiException;


/**
 * Example to show that different character sets may work some times. 
 *
 * @author Dennis
 */
public class Users extends Base {
    public boolean add(String username,String days[], String comment) {
        comment = "";
        try {
            String uptime = days[1].contains("D") ? "'"+days[0]+"d 00:00:00' " 
                    : days[1].contains("H") ? "'0d "+days[0]+":00:00'" 
                    : "'0d 00:"+days[0]+":00'" ;
            logger.log(Level.INFO,":uptime={0}", uptime);
            con.execute("/ip/hotspot/user/add"
                    + " name="+username
                    + " password="+username
                    + " server=all"
                    + " profile='"+MIKROTIK_PROFILE+"' "
                    + " limit-uptime="+MIKROTIK_LIMIT_UPTIME
            );
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}", ex);
            return false;
        }
    }
    
    public void update(String username,int days) {
        try {
            String uptime = days>0 ? "'"+days+"d 00:00:00' " : "'0d 01:00:00'" ;
            con.execute("/ip/hotspot/user/set"
                    + " name="+username
                    + " limit-uptime="+uptime);
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}", ex);
        }
    }
    
    public boolean remove(String id) {
        try {
            con.execute("/ip/hotspot/user/remove .id="+id);
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}", ex);
            return false;
        }
    }
    
    public List<Map<String, String>> getAll() {
        List<Map<String, String>> users = null;
        try {
            users = con.execute("/ip/hotspot/user/print");
            for (Map<String, String> res : users) {
                System.out.println(res.toString());
                // System.out.printf("%s : %s\n", res.get("name"), res.get("comment"));
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}", ex);
        }
        return users;
    }
    
    public Map<String, String> get(String username) {   
        Map<String, String> user = null;
        try {
            for (Map<String, String> res : con.execute("/ip/hotspot/user/print")) {
                if(res.get("name").equals(username)) user = res;
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}", ex);
        } finally {
            return user;
        }
    }
    
    public void resetUser(String id){
        try {
            con.execute("/ip/hotspot/user/reset-counters numbers='"+id+"'");
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}", ex);
        }
    }
    /*
    public static void main(String args[]){
        try {
            Users ex = new Users();
            ex.connect();
            Map<String, String> user = ex.get("0723111920");
            logger.log(Level.INFO, "Found hotspot user :{0}",user.toString());
            ex.resetUser(user.get(".id"));
            ex.disconnect();
        } catch (Exception ex) {
            logger.log(Level.SEVERE,"{0}", ex);
        }
    }
    */
}
