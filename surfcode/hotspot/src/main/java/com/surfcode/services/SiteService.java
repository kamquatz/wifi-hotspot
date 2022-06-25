package com.surfcode.services;
import static co.ke.lib.logs.Logging.logger;
import com.surfcode.Init;
import static com.surfcode.Init.BUCKET;
import static com.surfcode.Init.DB_HOST;
import static com.surfcode.Init.DB_NAME;
import static com.surfcode.Init.DB_PASS;
import static com.surfcode.Init.DB_USER;
import com.surfcode.models.Site;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.springframework.stereotype.Service;

@Service
public class SiteService implements ISiteService  {
    @Override
    public Site find(String name){
        //creating an object
        logger.log(Level.INFO, "{0}", "Linking site");
        Site site = new Site();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `sites` "
                        + "WHERE `name`=?");
                select.setObject(1, name);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    site.setId(result.getInt("id"));
                    site.setName(result.getString("name"));
                    site.setHost(result.getString("host"));
                    site.setPort(result.getInt("port"));
                    site.setUser(result.getString("user"));
                    site.setPass(result.getString("pass"));
                    site.setProfile(result.getString("profile"));
                    site.setLimit_uptime(result.getString("limit_uptime"));
                    site.setCreated_at(result.getString("created_at"));
                    site.setUpdated_at(result.getString("updated_at"));
                    logger.log(Level.INFO, "Linked site :name={0} :host={1}", new Object[]{site.getName(), site.getHost()});
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.toString());
        }
        return site;
    }
    
    @Override
    public List<Site> findActive(){
        //creating an object of ArrayList
        logger.log(Level.INFO, "{0}", "Fetching active sites");
        ArrayList<Site> sites = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `sites` "
                        + "WHERE `status`=? "
                        + "LIMIT ?");
                select.setObject(1, 1);
                select.setObject(2, BUCKET);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    Site site = new Site();
                    site.setId(result.getInt("id"));
                    site.setName(result.getString("name"));
                    site.setHost(result.getString("host"));
                    site.setPort(result.getInt("port"));
                    site.setUser(result.getString("user"));
                    site.setPass(result.getString("pass"));
                    site.setProfile(result.getString("profile"));
                    site.setLimit_uptime(result.getString("limit_uptime"));
                    site.setCreated_at(result.getString("created_at"));
                    site.setUpdated_at(result.getString("updated_at"));
                    logger.log(Level.INFO, "Found site :name={0} :host={1}", new Object[]{site.getName(), site.getHost()});
                    sites.add(site);
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, e.toString());
        }
        return sites;
    }
}
