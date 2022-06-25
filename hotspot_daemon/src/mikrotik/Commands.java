package mikrotik;

import static co.ke.lib.logs.Logging.logger;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import static main.Init.MIKROTIK_PROFILE;
import mikrotik.core.MikrotikApiException;
import static main.Init.MIKROTIK_LIMIT_UPTIME;

/**
 * Example to show that different character sets may work some times.
 *
 * @author Dennis
 */
public class Commands extends Base {

    public boolean reboot() {
        try {
            con.execute("/system/reboot"); // execute a command
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
            return false;
        }
    }

    // /ip hotspot walled-garden ip add action=accept disabled=no dst-host=*.multiplespacetechnologies.com
    // /ip hotspot walled-garden ip add action=accept disabled=no dst-host=*.multiplespacetechnologies.com
    public boolean addWalledGarden(String domain) {
        try {
            con.execute("/ip/hotspot/walled-garden/add "
                    + "action=allow "
                    + "disabled=no "
                    + "dst-host=" + domain); // execute a command
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
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
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return walledGardens;
    }

    public void removeAllWalledGardens() {
        try {
            for (Map<String, String> res : con.execute("/ip/hotspot/walled-garden/print")) {
                System.out.println(res.toString());
                con.execute("/ip/hotspot/walled-garden/remove "
                        + ".id=" + res.get(".id"));
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
    }

    public boolean addUser(String username) {
        try {
            con.execute("/ip/hotspot/user/add"
                    + " name=" + username
                    + " password=" + username
                    + " server=all"
                    + " profile='" + MIKROTIK_PROFILE + "'"
                 //   + " limit-uptime='" + MIKROTIK_LIMIT_UPTIME + "'"
            );
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
            return false;
        }
    }

    public boolean removeUser(String id) {
        try {
            con.execute("/ip/hotspot/user/remove .id=" + id);
            return true;
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
            return false;
        }
    }

    public Map<String, String> getUser(String username) {
        Map<String, String> user = null;
        try {
            for (Map<String, String> res : con.execute("/ip/hotspot/user/print")) {
                if (res.get("name").equals(username)) {
                    user = res;
                    System.out.println(res.toString());
                }
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        } finally {
            if (user == null) {
                System.out.println("User Not Found");
            }
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
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return users;
    }
    
    public List<Map<String, String>> getAllActiveUsers() {
        List<Map<String, String>> users = null;
        try {
            users = con.execute("/ip/hotspot/active/print");
            for (Map<String, String> res : users) {
                System.out.println(res.toString());
            }
        } catch (MikrotikApiException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return users;
    }

    public static void main(String args[]) {

        Commands c = new Commands();
        c.connect();
        List<Map<String, String>> allUsers = c.getAllUsers();
        
        /*
        for (Map<String, String> user : allUsers) 
             if(user.get("tag").contains("4"))    
                 c.removeUser(user.get(".id"));
         */
        
       // c.addWalledGarden("185.203.117.51");
        for(int i=1;i<255;i++) c.addUser(String.valueOf(i));
        
        System.out.println(c.getAllActiveUsers().size());
        System.out.println(c.getAllUsers().size());
        c.getAllWalledGardens();
        
        
        /*
        for (Map<String, String> user : c.getAllUsers()) {       
            if(user.get(".id").contains("*12")) 
                System.out.println(user);
                //c.removeUser(user.get(".id"));
        }
        System.out.println(c.getAllUsers().size());
         */
        c.disconnect();
/*
        Commands c = new Commands();
        c.connect();
        // Map<String, String> user = c.getUser("0721355811");
        // c.removeUser(user.get(".id"));
        //String username = "0721355811";
        //c.addUser(username,"'0d 00:10:00'");
        //c.getAllUsers();
        // c.reboot();
        // c.addWalledGardenDomain("185.203.117.51");
        c.getAllWalledGardens();
        // c.disconnect();      
*/
    }

}
