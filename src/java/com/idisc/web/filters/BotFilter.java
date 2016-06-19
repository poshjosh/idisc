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
    
    private final String cacheDir;
    
    private class BotCheckerImpl extends BotCheckerInMemoryCache {
        public BotCheckerImpl(String trapFilename) {
            super(trapFilename, memoryCeiling, memoryFloor, cacheDir);
        }
        public BotCheckerImpl(String trapFilename, boolean strict, int cacheSize) {
            super(trapFilename, strict, memoryCeiling, memoryFloor, cacheDir, cacheSize);
        }
        @Override
        protected boolean add(int botType, String value) {
            boolean added = super.add(botType, value);
            Level level = WebApp.getInstance().isDebug() ? Level.INFO : Level.FINER;
XLogger.getInstance().log(level, "Added {0} = {1}", this.getClass(), this.getBotTypeName(botType), value);
            return added;
        }
        @Override
        public boolean acceptHostOrAddress(String value) {
            if(WebApp.getInstance().isProductionMode()) {
                return super.acceptHostOrAddress(value);
            }else{
                return this.acceptAllNonnullOrEmptyHostOrAddresses(value);
            }
        }
        private boolean acceptAllNonnullOrEmptyHostOrAddresses(String value) {
            // particularly suited for allowing localhost i.e 127.0.0.1
            return value != null && value.length() > 0;
        }
    }
    
    public BotFilter() {
        Configuration configuration = WebApp.getInstance().getConfiguration();
        this.memoryCeiling = configuration.getLong(ConfigNames.BOTFILTER_DISABLE_AT_MEMORY_ABOVE, -1L);
        this.memoryFloor = configuration.getLong(ConfigNames.BOTFILTER_ENABLE_AT_MEMORY_BELOW, -1L);
        this.cacheDir = configuration.getString(ConfigNames.BOTFILTER_CACHEDIR, null);
XLogger.getInstance().log(Level.FINE, "BotFilter, memory ceiling: {0}, floor: {1}, cache dir: {2}", 
        this.getClass(), this.memoryCeiling, this.memoryFloor, this.cacheDir);
    }

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        boolean proceed = super.doBeforeProcessing(request, response);
        
        final String userAgent = request.getHeader("User-Agent");
        if(!proceed) {
            if(userAgent != null && userAgent.toLowerCase().contains("google")) {
                proceed = true;
            }else{
XLogger.getInstance().log(Level.FINER, "Denied: {0} to: {1}", 
        this.getClass(), request.getRequestURI(), userAgent);
            }
        }else{
            final String requestURI = request.getRequestURI();
            if(requestURI.contains("/feed/") || requestURI.contains("/feeds")) {
XLogger.getInstance().log(Level.FINER, "Allowed: {0} to: {1}", 
        this.getClass(), requestURI, userAgent);
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
        
        return new BotCheckerImpl("/adminAdminPage");
    }
}
