package com.surfcode.services;
import static co.ke.lib.logs.Logging.logger;
import static com.surfcode.Init.DB_HOST;
import static com.surfcode.Init.DB_NAME;
import static com.surfcode.Init.DB_PASS;
import static com.surfcode.Init.DB_USER;
import com.surfcode.models.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService  {
    @Override
    public boolean add(String phone, String code, int amount, int days) {
        boolean isAdded = false;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement create;
                lConnection.setAutoCommit(false);   
                create = lConnection.prepareStatement(""
                        + "INSERT INTO `customers` "
                        + "(`phone`,`code`,`amount`,`days`,`expiry_time`,`created_at`) "
                        + "VALUES(?,?,?,?,DATE_ADD(NOW(),INTERVAL ? DAY),NOW())");
                create.setObject(1, phone);
                create.setObject(2, code);
                create.setObject(3, amount);
                create.setObject(4, days);    
                create.setObject(5, days);      
                create.execute();
                logger.log(Level.INFO, "Added customer to database :phone={0}", phone);
                lConnection.commit();
                create.close();                
                isAdded = true;
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
         
        return isAdded;
    }
    
    @Override
    public Customer getActive(String phone){
        //creating an object
        Customer customer = new Customer();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `customers` "
                        + "WHERE `phone`= ? "
                        + "AND `expiry_time`>NOW()");
                select.setObject(1, phone);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    customer.setId(result.getInt("id"));
                    customer.setPhone(result.getString("phone"));
                    customer.setCode(result.getString("code"));
                    customer.setAmount(result.getInt("amount"));
                    customer.setDays(result.getInt("days"));
                    customer.setExpiry_time(result.getString("expiry_time"));
                    customer.setCreated_at(result.getString("created_at"));
                    customer.setUpdated_at(result.getString("updated_at"));
                    customer.setActive(true);
                    
                    logger.log(Level.INFO, "Found customer :phone={0}", phone);
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
        return customer;
    }
}
