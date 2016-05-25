package com.idisc.web.filters;

import com.bc.util.XLogger;
import com.bc.web.botchecker.BotChecker;
import com.bc.web.botchecker.BotCheckerInMemoryCache;
import com.idisc.web.AppProperties;
import com.idisc.web.WebApp;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;

/**
 * @author poshjosh
 */
public class BotFilter extends com.bc.web.core.filters.BotFilter {
    
    private final long memoryCeiling;
    
    private final long memoryFloor;
    
    public BotFilter() {
        Configuration configuration = WebApp.getInstance().getConfiguration();
        this.memoryCeiling = configuration.getLong(
                AppProperties.BOTFILTER_DISABLE_AT_MEMORY_ABOVE, -1L);
        this.memoryFloor = configuration.getLong(
                        AppProperties.BOTFILTER_ENABLE_AT_MEMORY_BELOW, -1L);
XLogger.getInstance().log(Level.FINE, "BotFilter, memory ceiling: {0}, floor: {1}", 
        this.getClass(), this.memoryCeiling, this.memoryFloor);
    }

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        final boolean isNotABot = super.doBeforeProcessing(request, response);
        
        if(isNotABot && request.getRequestURI().contains("/feeds")) {
XLogger.getInstance().log(Level.INFO, 
        "#doBeforeProcessing. Is bot: {0}, user-agent: {1}", 
        this.getClass(), !isNotABot, request.getHeader("User-Agent"));
        }

        return isNotABot;
    }
    @Override
    protected BotChecker createBotChecker() {
        
//        String dir = WebApp.getInstance().getConfiguration().getString(AppProperties.BOTCONFIGS_DIR);
        
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
