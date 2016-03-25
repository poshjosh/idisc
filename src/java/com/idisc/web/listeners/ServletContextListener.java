package com.idisc.web.listeners;

import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.core.FeedUpdateService;
import com.idisc.core.FeedUpdateTask;
import com.idisc.web.AppProperties;
import com.idisc.web.IdiscUpdateTask;
import com.idisc.web.WebApp;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.apache.commons.configuration.Configuration;

/**
 * Web application lifecycle listener.
 * @author Josh
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        try{
            
XLogger.getInstance().setRootOnly(true);
XLogger.getInstance().setRootLoggerName(WebApp.class.getName());
            
            ServletContext sc = sce.getServletContext();
            
            this.updateAttributesFromInitParameters(sc);
            
            WebApp.getInstance().init(sc);
            
            IdiscApp app = IdiscApp.getInstance();

            boolean devMode = true; 
            
            if(devMode) {
                FeedUpdateTask.LOG_LEVEL = Level.INFO;
            }else{
                FeedUpdateTask.LOG_LEVEL = Level.FINE;
            }
            
            String propertiesFilename = devMode ? 
// These are not working yet                    
//                    "META-INF/properties/idisc_scrapper_devmode.properties" : 
//                   "META-INF/properties/idisc_scrapper.properties";
                    "META-INF/properties/idisccore_scrapper_devmode.properties" : 
                   "META-INF/properties/idisccore_scrapper.properties";
            app.setScrapperPropertiesFilename(propertiesFilename);
 
            String persistenceFilename = devMode ?
                    "META-INF/persistence_remote.xml" : "META-INF/persistence.xml";

            app.setPersistenceFilename(persistenceFilename);
            
            app.init();
            
            Configuration config = WebApp.getInstance().getConfiguration();
            
            sc.setAttribute(AppProperties.APP_NAME, config.getString(AppProperties.APP_NAME));
            
            int delay = config.getInt(AppProperties.FEED_CYCLE_DELAY);
            int interval = config.getInt(AppProperties.FEED_CYCLE_INTERVAL);
            
            FeedUpdateService fus = new FeedUpdateService(){
                @Override
                public FeedUpdateTask newTask() {
                    return new IdiscUpdateTask();
                }
            };
            
            fus.start(delay, interval, TimeUnit.MINUTES);
            
//            List sendTimes = config.getList(AppProperties.SEND_MAIL_TIMES);

//            if(sendTimes != null && !sendTimes.isEmpty()) {
//                for(Object sendTime:sendTimes) {
//                    if(sendTime == null) {
//                        continue;
//                    }
//                    String sval = sendTime.toString().trim();
//                    if(sval.isEmpty()) {
//                        continue;
//                    }
//                    SendNewsAsEmailService SVC = new SendNewsAsEmailService();
//                    SVC.startAt(sendTime.toString());
//                }
//            }
        }catch(Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("This program has to exit due to the following problem:", e);
        }
    }
    
    private void updateAttributesFromInitParameters(ServletContext context) {
        
        Enumeration en = context.getInitParameterNames();
        
        while(en.hasMoreElements()) {
            
            String key = (String)en.nextElement();
            Object val = context.getInitParameter(key);
Logger.getLogger(this.getClass().getName()).log(Level.FINE, 
"Setting context attribute from init param: {0}={1}", new Object[]{key, val});
            context.setAttribute(key, val);
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
