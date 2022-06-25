package com.surfcode;

import static co.ke.lib.logs.Logging.ConfigLoggers;
import java.util.LinkedHashMap;
import ke.co.lib.files.ConfigParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author dennis
 * 
 * App entry point
 */
@SpringBootApplication
public class Init{
    final static LinkedHashMap PROPS = ConfigParser.ReadProps("config");
    public final static String  
            APP_NAME = (String)PROPS.get("app"), 
            DB_HOST = (String)PROPS.get("db_host"),
            DB_NAME = (String)PROPS.get("db_name"),
            DB_USER = (String)PROPS.get("db_user"),
            DB_PASS = (String)PROPS.get("db_pass"); 
    public final static int 
            PACKAGE_DAILY = Integer.valueOf((String)PROPS.get("daily")),
            PACKAGE_WEEKLY = Integer.valueOf((String)PROPS.get("weekly")),
            PACKAGE_MONTHLY = Integer.valueOf((String)PROPS.get("monthly"));
    static{        
        ConfigLoggers((String) PROPS.get("log"),APP_NAME);
    }
    
    public static void main(String args[]){   
        SpringApplication.run(Init.class, args);
    }
}
