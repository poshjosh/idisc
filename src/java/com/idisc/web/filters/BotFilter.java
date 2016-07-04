package com.idisc.web.filters;

import com.bc.util.JsonBuilder;
import com.bc.util.XLogger;
import com.bc.web.botchecker.BotChecker;
import com.bc.web.botchecker.BotCheckerInMemoryCache;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
import com.idisc.web.ConfigNames;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.JSONParser;

/**
 * @author poshjosh
 */
public class BotFilter extends com.bc.web.core.filters.BotFilter {
    
    private class BotCheckerImpl extends BotCheckerInMemoryCache {
        private final boolean debug;
        private final boolean productionMode;
        public BotCheckerImpl(
                String trapFilename, long memoryCeiling, 
                long memoryFloor, String cacheDir, 
                boolean productionMode, boolean debug) {
            super(trapFilename, memoryCeiling, memoryFloor, cacheDir);
            this.debug = debug;
            this.productionMode = productionMode;
        }
        public BotCheckerImpl(
                String trapFilename, boolean strict, 
                long memoryCeiling, long memoryFloor, 
                String cacheDir, int cacheSize,
                boolean productionMode, boolean debug) {
            super(trapFilename, strict, memoryCeiling, memoryFloor, cacheDir, cacheSize);
            this.debug = debug;
            this.productionMode = productionMode;
        }

        @Override
        public String getFilename(String key) {
            return this.getCacheDir()+'/'+key+".json";
        }

        @Override
        public List<String> load(File file) throws IOException, ClassNotFoundException {
            
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))){
                
                try{
                    
                    JSONParser parser = new JSONParser();
                    
                    Map output = (Map)parser.parse(reader);
                    
                    return (List<String>)output.get(file.getName());
                    
                }catch(org.json.simple.parser.ParseException e) {
                 
                    throw new IOException(e);
                }
            }
        }

        @Override
        public void save(List<String> toSave, File file) throws IOException {
            
            Map output = Collections.singletonMap(file.getName(), toSave);
            
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
             
                new JsonBuilder(true).appendJSONString(output, writer);
                
                writer.flush();
            }
        }
        @Override
        protected boolean add(int botType, String value) {
            boolean added = super.add(botType, value);
            Level level = debug ? Level.INFO : Level.FINER;
if(added) XLogger.getInstance().log(level, "Added {0} = {1}", this.getClass(), this.getBotTypeName(botType), value);
            return added;
        }
        @Override
        public boolean acceptHostOrAddress(String value) {
            if(productionMode) {
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
    
    public BotFilter() { }

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
    protected BotChecker createBotChecker(HttpServletRequest request) {
        
        AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        Configuration configuration = appContext.getConfiguration();
        
        final long memoryCeiling = configuration.getLong(ConfigNames.BOTFILTER_DISABLE_AT_MEMORY_ABOVE, -1L);
        final long memoryFloor = configuration.getLong(ConfigNames.BOTFILTER_ENABLE_AT_MEMORY_BELOW, -1L);
        final String cacheDir = configuration.getString(ConfigNames.BOTFILTER_CACHEDIR, null);
XLogger.getInstance().log(Level.INFO, "BotFilter, memory ceiling: {0}, floor: {1}, cache dir: {2}", 
        this.getClass(), memoryCeiling, memoryFloor, cacheDir);
        
        if(memoryCeiling < 0 && memoryFloor < 0) {
            
            return null;
        }
        
        return new BotCheckerImpl(
                "/adminAdminPage", memoryCeiling, memoryFloor, cacheDir,
                appContext.isProductionMode(), appContext.isDebug());
    }
}
