package mobiclick;

/**
 *
 * @author dennis
 */
import static co.ke.lib.logs.Logging.logger;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import static main.Init.*;
import mikrotik.Commands;
import mikrotik.Users;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteDb {      
    public void addCustomers(){
        for(Object o : getCustomers()){ 
            try {
                JSONObject object = (JSONObject) o;
                int id = object.getInt("id");
                String account = object.getString("phone").replaceAll(" ", "");
                //account = "0"+account.substring(-9);
                
                logger.log(Level.INFO, "Found :customer={0}",
                        new Object[]{account});
                Commands command = new Commands();
                if(command.connect()){   
                    Map<String, String> user = command.getUser(account);
                    if(user==null) {
                        command.addUser(account);                        
                        logger.log(Level.INFO, "added hotspot user :phone={0} :uptime={1}",new Object[]{account});  
                    }
                    else if(user.get("profile").equalsIgnoreCase(MIKROTIK_PROFILE) && user.get("limit-uptime")!=null){
                        if(user.get("limit-uptime").equals(user.get("uptime"))){
                            logger.log(Level.INFO, "Found hotspot user :{0}",user.toString());
                            command.removeUser(user.get(".id"));    
                            logger.log(Level.INFO, "removed hotspot user :phone={0}",account);
                            command.addUser(account);  
                            logger.log(Level.INFO, "added hotspot user :phone={0} :uptime={1}",new Object[]{account,user.get("uptime")});                   
                        }
                    }
                    command.disconnect();
                    logger.log(Level.INFO, "updating customer :id={0}",id);
                    updateNewCustomer(id);                     
                }
            } catch (JSONException ex1) {
                logger.log(Level.SEVERE, "{0}", ex1);
            }
        }        
    }
    
    public JSONArray removeCustomers(){
        JSONArray customers = getCustomers();
        for(Object o : customers){ 
            int currentId;
            try {
                JSONObject object = (JSONObject) o;
                currentId = object.getInt("id");
                String account = object.getString("account");
                String site = object.getString("site").replaceAll(MIKROTIK_SITE, "");
                
                logger.log(Level.INFO, "Found :customer={0}", account);
                
                Users ex = new Users();
                ex.connect();
                Map<String, String> user = ex.get(account);
                if(user!=null){
                    logger.log(Level.INFO, "Found hotspot user :{0}",user.toString());
                    ex.remove(user.get(".id"));
                }
                ex.disconnect();
                
                logger.log(Level.INFO, "updating customer :id={0}",2);
                updateNewCustomer(currentId);                     
            } catch (JSONException ex) {
                logger.log(Level.SEVERE, "{0}", ex);
            } 
        }    
        return customers;
    }


    private JSONArray getCustomers(){
        JSONArray jsonArr = new JSONArray();
        try {
            String url = CUSTOMER_GET_URL
                    + "?site="+MIKROTIK_SITE
                    + "&limit="+BUCKET;
            //System.out.println(url);
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            int responseCode = httpClient.getResponseCode();
            logger.log(Level.INFO, "Sending GET request");
            logger.log(Level.INFO, "ResponseCode :{0}", responseCode);
            
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                
                logger.log(Level.INFO, "{0}",response.toString());
                jsonArr = new JSONArray(response.toString());
                  
            } catch (Exception ex) {
                logger.log(Level.SEVERE,"{0}", ex);
            }
            
        }   catch (IOException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
        return jsonArr;

    }
    
    private void updateNewCustomer(int id) {
        try {
            String url = CUSTOMER_UPDATE_URL;
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            
            String urlParameters = "id="+id+"&site="+MIKROTIK_SITE;
            
            // Send post request
            httpClient.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "{0}", ex);
            }
            
            int responseCode = httpClient.getResponseCode();
            logger.log(Level.INFO, "Sending POST request");
            logger.log(Level.INFO, "POST parameters :{0}", urlParameters);
            logger.log(Level.INFO, "ResponseCode :{0}", responseCode);
            
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                
                logger.log(Level.INFO, "{0}", response.toString());
                
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "{0}", ex);
            }
        }   catch (MalformedURLException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "{0}", ex);
        }
    }
}
