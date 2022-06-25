package com.surfcode.models;

import static co.ke.lib.logs.Logging.logger;

import java.sql.*;
import java.util.logging.Level;

import static com.surfcode.Init.*;

/**
 *
 * @author dennis
 * 
 * This class interacts with the database tables
 */
public class Db {
    public void addCustomers(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select,update;
                lConnection.setAutoCommit(false);
                select = lConnection.prepareStatement("SELECT `id`, `phone`, `days`, `mpesa_code`, `amount`, `expiry_time` "
                        + "FROM `customers` "
                        + "WHERE `device`=0 "
                        + "LIMIT ?");
                update = lConnection.prepareStatement("UPDATE `customers` "
                        + "SET `device`=? "
                        + "WHERE `id`=?");
                select.setObject(1, BUCKET);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    int id = result.getInt("id");
                    String phone = result.getString("phone");
                    String days = result.getString("days");
                    String[] split = days.split(" ");
                    String comment = ":mpesa="+result.getString("mpesa_code")
                            + " :amount="+result.getString("amount")
                            + " :days="+days
                            + " :expiry="+result.getString("expiry_time");
                    logger.log(Level.INFO, "Found :customer={0} :comment={1} :subscription days={2}",
                            new Object[]{phone,comment,days});
                    /*
                    Users ex = new Users();
                    ex.connect();
                    ex.add(phone,split,comment);
                    ex.disconnect();            
                    */
                    update.setObject(1, 1);
                    update.setObject(2, id);
                    
                    logger.log(Level.INFO, "updating customer :id={0}",id);
                    update.addBatch();
                }   
                int rows = update.executeBatch().length;
                logger.log(Level.INFO, "updated :rows={0}", rows);
                lConnection.commit();
                select.close();
                update.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.toString());
        }
    }
    
    public void removeCustomers(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select,update;
                lConnection.setAutoCommit(false);
                select = lConnection.prepareStatement("SELECT `id`, `phone`, `days`, `mpesa_code`, `amount`, `expiry_time` "
                        + "FROM `customers` "
                        + "WHERE `device`=1 AND `expiry_time`<=NOW() "
                        + "LIMIT ?");
                update = lConnection.prepareStatement("UPDATE `customers` "
                        + "SET `device`=? "
                        + "WHERE `id`=?");
                select.setObject(1, BUCKET);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    int id = result.getInt("id");
                    String phone = result.getString("phone");
                    String days = result.getString("days");
                    int device = -1;
                    String comment = ":mpesa="+result.getString("mpesa_code")
                            + " :amount="+result.getString("amount")
                            + " :days="+days
                            + " :expiry="+result.getString("expiry_time");
                    logger.log(Level.INFO, "Found :customer={0} :comment={1} :subscription days={2}",
                            new Object[]{phone,comment,days});
                    /*
                        Users ex = new Users();
                        ex.connect();
                        Map<String, String> user = ex.get(phone);
                        logger.log(Level.INFO, "found customer :{0}",user.toString());
                        ex.remove(user.get(".id"));
                        ex.disconnect();
                    */                    

                    update.setObject(1, device);
                    update.setObject(2, id);
                    
                    logger.log(Level.INFO, "removing customer :id={0}",id);
                    update.addBatch();
                }   
                int rows = update.executeBatch().length;
                logger.log(Level.INFO, "removed :rows={0}", rows);
                lConnection.commit();
                select.close();
                update.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.toString());
        } 
    }
}
