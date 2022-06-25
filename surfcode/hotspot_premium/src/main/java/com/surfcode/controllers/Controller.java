package com.surfcode.controllers;
import static co.ke.lib.logs.Logging.logger;
import static com.surfcode.Init.PACKAGE_DAILY;
import static com.surfcode.Init.PACKAGE_MONTHLY;
import static com.surfcode.Init.PACKAGE_WEEKLY;
import com.surfcode.models.Customer;
import com.surfcode.models.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.surfcode.services.IStationService;
import java.util.Map;
import java.util.logging.Level;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.surfcode.services.ICustomerService;
import com.surfcode.services.ILoginService;


/**
 *
 * @author dennis
 */

@RestController
public class Controller{
    @Autowired
    private ILoginService loginService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IStationService stationService;
    
    @PostMapping("/account")
    public int add( @RequestParam Map<String,Object> body) {
        int accountId = 0;
        String phone = body.get("phone").toString();
        String town = body.get("town").toString();
        String stationName = body.get("station").toString();
        
        logger.log(Level.INFO, "Incoming request :phone={0} :town={1} :station={2}", new Object[]{phone, town, stationName});        
        
        Customer customer = customerService.getActive(phone);
        if(customer.isActive()){
            Station station = stationService.find(stationName, town);
            accountId  = loginService.add(customer, station);
            stationService.update(station, accountId);
        }else stkPush(phone, 20);
        
        return accountId;
    }
    
    @PostMapping("/sync")
    public boolean sync( @RequestParam Map<String,Object> body) {
        boolean isAdded = false;
        String from = body.get("from").toString();
        String message = body.get("message").toString();
        logger.log(Level.INFO, "Incoming request :from={0} :message={1}", new Object[]{from, message});   
        
        if(from.equalsIgnoreCase("MPESA")){
            String[] parts = message.split(" ");
            String phone = parts[8];
            String code = parts[0];
            double amount = Double.valueOf(parts[5].substring(5));
            int days;
            
            if(amount <= PACKAGE_DAILY) days = (int) (amount/PACKAGE_DAILY);
            else if(amount <= PACKAGE_WEEKLY) days = (int) (amount*7/PACKAGE_WEEKLY); 
            else days = (int) (amount*31/PACKAGE_MONTHLY); 
            
            isAdded = customerService.add(phone, code, (int) amount, days);            
        }
        
        return isAdded;
    }
    
    private boolean stkPush(String phone, int amount){
        boolean isPushed = false;
        
        return isPushed;
    }
}
