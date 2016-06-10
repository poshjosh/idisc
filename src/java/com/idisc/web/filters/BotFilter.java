package com.idisc.web.filters;

import com.bc.util.XLogger;
import com.bc.web.botchecker.BotChecker;
import com.bc.web.botchecker.BotCheckerInMemoryCache;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
import com.idisc.web.ConfigNames;

/**
 * @author poshjosh
 */
public class BotFilter extends com.bc.web.core.filters.BotFilter {
    
    private final long memoryCeiling;
    
    private final long memoryFloor;
    
    public BotFilter() {
        Configuration configuration = WebApp.getInstance().getConfiguration();
        this.memoryCeiling = configuration.getLong(ConfigNames.BOTFILTER_DISABLE_AT_MEMORY_ABOVE, -1L);
        this.memoryFloor = configuration.getLong(ConfigNames.BOTFILTER_ENABLE_AT_MEMORY_BELOW, -1L);
XLogger.getInstance().log(Level.FINE, "BotFilter, memory ceiling: {0}, floor: {1}", 
        this.getClass(), this.memoryCeiling, this.memoryFloor);
    }

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        boolean proceed = super.doBeforeProcessing(request, response);
        
        if(!proceed) {
            String s = request.getHeader("User-Agent");
            if(s != null && s.toLowerCase().contains("google")) {
                proceed = true;
            }
        }
        
//        if(proceed) {
//            if(request.getRequestURI().contains("/feeds")) {
//XLogger.getInstance().log(
//        Level.INFO, "Allowed: {0}, request URI: {1}, user-agent: {2}", 
//        this.getClass(), proceed, request.getRequestURI(), request.getHeader("User-Agent"));
//            }
//        }

        return proceed;
    }
    @Override
    protected BotChecker createBotChecker() {
        
//        String dir = WebApp.getInstance().getConfiguration().getString(ConfigNames.BOTCONFIGS_DIR);
        
//        if(dir == null || (this.memoryCeiling < 0 && this.memoryFloor < 0)) {
        if(this.memoryCeiling < 0 && this.memoryFloor < 0) {
            
            return null;
        }
        
        if(!WebApp.getInstance().isProductionMode()) {
        
            return new BotCheckerInMemoryCache("/adminAdminPage", this.memoryCeiling, this.memoryFloor){
                
                @Override
                public boolean acceptHostOrAddress(String value) {
                    
                    // Allows all hosts/addresses, 
                    // particularly suited for allowing localhost i.e 127.0.0.1
                    //
                    return value != null && value.length() > 0;
                }
            }; 
            
        }else{
            
            return new BotCheckerInMemoryCache("/adminAdminPage", this.memoryCeiling, this.memoryFloor); 
        }
    }
}
