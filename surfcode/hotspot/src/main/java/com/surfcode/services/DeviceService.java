package com.surfcode.services;
import static co.ke.lib.logs.Logging.logger;
import static com.surfcode.Init.BUCKET;
import static com.surfcode.Init.DB_HOST;
import static com.surfcode.Init.DB_NAME;
import static com.surfcode.Init.DB_PASS;
import static com.surfcode.Init.DB_USER;
import com.surfcode.mikrotik.Commands;
import com.surfcode.models.Device;
import com.surfcode.models.Site;
import com.surfcode.models.Station;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.springframework.stereotype.Service;

@Service
public class DeviceService implements IDeviceService  {
    @Override
    public boolean add(String phone, Site site, Station station) {
        boolean isAdded = false;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement create;
                lConnection.setAutoCommit(false);   
                create = lConnection.prepareStatement(""
                                + "INSERT INTO `"+site.getName()+"` (`phone`,`station`,`device`, `connections`,`expiry_time`, `created_at`) "
                                + "VALUES(?,?,?,?,DATE_ADD(NOW(),INTERVAL 30 MINUTE),NOW()) "
                                + "ON DUPLICATE KEY UPDATE "
                                + "`station`=?,"
                                + "`device`=?,"
                                + "`connections`=(`connections`+1),"
                                + "`expiry_time`=DATE_ADD(NOW(),INTERVAL 30 MINUTE)");
                create.setObject(1, phone);
                create.setObject(2, station.getName());
                create.setObject(3, 1);
                create.setObject(4, 1);    
                create.setObject(5, station.getName()); 
                create.setObject(6, 1);          
                create.execute();
                logger.log(Level.INFO, "Added device to database :phone={0}", phone);
                lConnection.commit();
                create.close();
                
                Commands command = new Commands();
                if(command.connect(site)){   
                    Map<String, String> user = command.getUser(phone);
                    if(user==null) {
                        isAdded = command.addUser(site,phone);                        
                        logger.log(Level.INFO, "Added hotspot user :phone={0} :uptime={1}",new Object[]{phone, site.getLimitUptime()});  
                    }
                    else if(user.get("profile").equalsIgnoreCase(site.getProfile()) && user.get("limit-uptime")!=null){
                        if(user.get("limit-uptime").equals(user.get("uptime"))){
                            logger.log(Level.INFO, "Found hotspot :user={0}",user.toString());
                            command.removeUser(user.get(".id"));    
                            logger.log(Level.INFO, "Removed hotspot user :phone={0}", phone);
                            isAdded = command.addUser(site,phone);  
                            logger.log(Level.INFO, "Added hotspot user :phone={0} :uptime={1}",new Object[]{phone, site.getLimitUptime()});                   
                        }
                    }
                    command.disconnect();               
                }
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
    
        return isAdded;
    }
    
    @Override
    public boolean update(Device device, Site site) {
        boolean isUpdated = false;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement update;
                lConnection.setAutoCommit(false);   
                update = lConnection.prepareStatement(""
                                + "UPDATE `"+site.getName()+"` SET "
                                + "`device`=? "
                                + "WHERE `id`=?");
                update.setObject(1, device.getDevice());
                update.setObject(2, device.getId());        
                isUpdated = update.execute();
                logger.log(Level.INFO, "updated device :phone={0}", device.getPhone());
                lConnection.commit();
                update.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
    
        return isUpdated;
    }
    
    @Override
    public Device find(String phone, String site){
        //creating an object
        Device device = new Device();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `"+site+"` "
                        + "WHERE `phone`= ?");
                select.setObject(1, phone);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    device.setId(result.getInt("id"));
                    device.setPhone(result.getString("phone"));
                    device.setDevice(result.getInt("device"));
                    device.setConnections(result.getInt("connections"));
                    device.setExpiry_time(result.getString("expiry_time"));
                    device.setCreated_at(result.getString("created_at"));
                    device.setUpdated_at(result.getString("updated_at"));
                    
                    logger.log(Level.INFO, "Found device :phone={0}", phone);
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
        return device;
    }
    
    @Override
    public List<Device> findAll(Site site){
        //creating an object of ArrayList
        logger.log(Level.INFO, "{0}", "Fetching all devices");
        ArrayList<Device> devices = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `"+site.getName()+"` "
                        + "LIMIT ?");
                select.setObject(1, BUCKET);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    Device device = new Device();
                    device.setId(result.getInt("id"));
                    device.setPhone(result.getString("phone"));
                    device.setDevice(result.getInt("device"));
                    device.setConnections(result.getInt("connections"));
                    device.setExpiry_time(result.getString("expiry_time"));
                    device.setCreated_at(result.getString("created_at"));
                    device.setUpdated_at(result.getString("updated_at"));
                    devices.add(device);
                    
                    logger.log(Level.INFO, "Found device :phone={0}", device.getPhone());
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING,"{0}", e.toString());
        }
        return devices;
    }
    
    @Override
    public List<Device> findAllExpired(Site site){
        //creating an object of ArrayList
        logger.log(Level.INFO, "{0}", "Fetching expired devices");
        ArrayList<Device> devices = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `"+site.getName()+"` "
                        + "WHERE expiry_time<NOW() AND `device`=? "
                        + "LIMIT ?");
                select.setObject(1, 1);
                select.setObject(2, BUCKET);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    Device device = new Device();
                    device.setId(result.getInt("id"));
                    device.setPhone(result.getString("phone"));
                    device.setDevice(result.getInt("device"));
                    device.setConnections(result.getInt("connections"));
                    device.setExpiry_time(result.getString("expiry_time"));
                    device.setCreated_at(result.getString("created_at"));
                    device.setUpdated_at(result.getString("updated_at"));
                    devices.add(device);
                    
                    logger.log(Level.INFO, "Found device :phone={0}", device.getPhone());
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
        return devices;
    }
}
