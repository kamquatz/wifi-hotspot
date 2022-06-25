package com.surfcode.mikrotik;

import static co.ke.lib.logs.Logging.logger;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import com.surfcode.mikrotik.core.MikrotikApiException;
import com.surfcode.models.Site;


/**
 * Example to show that different character sets may work some times. 
 *
 * @author Dennis
 */
public class Commands extends Base {        
    public boolean reboot(){
        try {
            con.execute("/system/reboot"); // execute a command
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
            return false;
        }
    }
    
    // /ip hotspot walled-garden ip add action=accept disabled=no dst-host=*.multiplespacetechnologies.com
    // /ip hotspot walled-garden ip add action=accept disabled=no dst-host=*.multiplespacetechnologies.com
    public boolean addWalledGarden(String domain){
        try {
            con.execute("/ip/hotspot/walled-garden/add "
                    + "action=allow "
                    + "disabled=no "
                    + "dst-host="+ domain); // execute a command
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
            return false;
        }
    }   
    
    public List<Map<String, String>> getAllWalledGardens() {
        List<Map<String, String>> walledGardens = null;
        try {
            walledGardens = con.execute("/ip/hotspot/walled-garden/print");
            for (Map<String, String> res : walledGardens) {
                System.out.println(res.toString());
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
        }
        return walledGardens;
    }  
    
    public void removeAllWalledGardens() {
        try {
            for (Map<String, String> res : con.execute("/ip/hotspot/walled-garden/print")) {
                System.out.println(res.toString());
                con.execute("/ip/hotspot/walled-garden/remove "
                        + ".id="+res.get(".id"));
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
        }
    }  
    
    public boolean addUser(Site site, String username) {
        try {
            con.execute("/ip/hotspot/user/add"
                    + " name="+username
                    + " password="+username
                    + " server=all"
                    + " profile='"+site.getProfile()+"'"
                    + " limit-uptime='"+site.getLimitUptime()+"'"
            );
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
            return false;
        }
    }
    
    public boolean removeUser(String id) {
        try {
            con.execute("/ip/hotspot/user/remove .id="+id);
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
            return false;
        }
    }
    
    public Map<String, String> getUser(String username) {   
        Map<String, String> user = null;
        try {
            for (Map<String, String> res : con.execute("/ip/hotspot/user/print")) {
                if(res.get("name").equals(username)) {
                    user = res;
                    System.out.println(res.toString());
                }                
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
        } finally {         
            if(user==null) System.out.println("User Not Found");
            return user;
        }
    }
    
    public List<Map<String, String>> getAllUsers() {
        List<Map<String, String>> users = null;
        try {
            users = con.execute("/ip/hotspot/user/print");
            for (Map<String, String> res : users) {
                System.out.println(res.toString());
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE,"{0}",  ex.toString());
        }
        return users;
    }
    
}
