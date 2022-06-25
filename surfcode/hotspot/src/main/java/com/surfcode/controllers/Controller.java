package com.surfcode.controllers;
import static co.ke.lib.logs.Logging.logger;
import com.surfcode.models.Site;
import com.surfcode.models.Station;
import com.surfcode.services.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.surfcode.services.ISiteService;
import com.surfcode.services.IStationService;
import java.util.Map;
import java.util.logging.Level;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author dennis
 */

@RestController
public class Controller{
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IStationService stationService;
    
    @PostMapping("/add")
    public int add( @RequestParam Map<String,Object> body) {
        int account = 0;
        String phone = body.get("phone").toString();
        String siteName = body.get("site").toString();
        Object st = body.get("station");
        logger.log(Level.INFO, "Incoming request :phone={0} :site={1} :station={2}", new Object[]{phone, siteName,body.get("station")});        
        
        Site site = siteService.find(siteName);    
        Station station = st!=null ? stationService.find(st.toString(), siteName) : new Station();
        boolean addDevice = deviceService.add(phone, site, station);
        account = station.getLast_user()+1;
        boolean updateStation = account<255 ? stationService.update(station, site, account) : stationService.update(station, site, 0);
        
        return account;
    }
}
