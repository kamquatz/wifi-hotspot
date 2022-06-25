package com.surfcode.services;
import static co.ke.lib.logs.Logging.logger;
import static com.surfcode.Init.DB_HOST;
import static com.surfcode.Init.DB_NAME;
import static com.surfcode.Init.DB_PASS;
import static com.surfcode.Init.DB_USER;
import com.surfcode.models.Customer;
import com.surfcode.models.Station;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService  {
    @Override
    public int add(Customer customer, Station station) {
        int accountId = 0;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                int nextUser = station.getLastuser()+1;
                accountId = nextUser<255 ? nextUser : 1;
                PreparedStatement create;
                lConnection.setAutoCommit(false);   
                create = lConnection.prepareStatement(""
                        + "INSERT INTO `logins` "
                        + "(`customer_id`,`station_id`,`account_id`,`created_at`) "
                        + "VALUES(?,?,?,NOW())");
                create.setObject(1, customer.getId());
                create.setObject(2, station.getId());
                create.setObject(3, accountId);
                create.execute();
                logger.log(Level.INFO, "Added login-account to database :accountId={0}", accountId);
                lConnection.commit();
                create.close();                
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
         
        return accountId;
    }
}
