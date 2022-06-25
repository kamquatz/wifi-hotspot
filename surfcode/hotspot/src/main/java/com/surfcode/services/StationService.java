package com.surfcode.services;
import static co.ke.lib.logs.Logging.logger;
import static com.surfcode.Init.BUCKET;
import static com.surfcode.Init.DB_HOST;
import static com.surfcode.Init.DB_NAME;
import static com.surfcode.Init.DB_PASS;
import static com.surfcode.Init.DB_USER;
import com.surfcode.models.Site;
import com.surfcode.models.Station;
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
public class StationService implements IStationService  {    
    @Override
    public boolean update(Station station, Site site, int lastUser) {
        boolean isUpdated = false;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement update;
                lConnection.setAutoCommit(false);   
                update = lConnection.prepareStatement(""
                                + "UPDATE `stations` SET "
                                + "`last_user`=? "
                                + "WHERE `id`=?");
                update.setObject(1, lastUser);
                update.setObject(2, station.getId());        
                isUpdated = update.execute();
                logger.log(Level.INFO, "updated station :station={0}", station.getName());
                lConnection.commit();
                update.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
    
        return isUpdated;
    }
    
    @Override
    public Station find(String name, String site){
        //creating an object
        Station station = new Station();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection lConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+DB_HOST+":3306/"+DB_NAME,DB_USER,DB_PASS)) {
                PreparedStatement select;
                select = lConnection.prepareStatement(""
                        + "SELECT * FROM `stations` "
                        + "WHERE `name`= ? "
                        + "AND `site`=?");
                select.setObject(1, name);
                select.setObject(2, site);
                ResultSet result = select.executeQuery();
                while (result.next()) {
                    station.setId(result.getInt("id"));
                    station.setName(result.getString("name"));
                    station.setLast_user(result.getInt("last_user"));
                    station.setCreated_at(result.getString("created_at"));
                    station.setUpdated_at(result.getString("updated_at"));
                    
                    logger.log(Level.INFO, "Found station :station={0}", name);
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "{0}", e.toString());
        }
        return station;
    }
    
    @Override
    public List<Station> findAll(Site site){
        //creating an object of ArrayList
        logger.log(Level.INFO, "{0}", "Fetching all stations");
        ArrayList<Station> stations = new ArrayList<>();
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
                    Station station = new Station();
                    station.setId(result.getInt("id"));
                    station.setName(result.getString("name"));
                    station.setLast_user(result.getInt("last_user"));
                    station.setCreated_at(result.getString("created_at"));
                    station.setUpdated_at(result.getString("updated_at"));
                    stations.add(station);
                    
                    logger.log(Level.INFO, "Found station :name={0}", station.getName());
                }   
                select.close();
            }
        }catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.WARNING,"{0}", e.toString());
        }
        return stations;
    }
    
}
