package com.idisc.web.filters;

import com.bc.util.JsonBuilder;
import com.bc.web.botchecker.BotChecker;
import com.bc.web.botchecker.BotCheckerDevMode;
import com.bc.web.botchecker.BotCheckerImpl;
import com.bc.web.botchecker.BotCheckerVoid;
import com.bc.web.botchecker.BotDiscCacheJson;
import com.bc.web.botchecker.BotDiscCacheJsonDevMode;
import com.bc.web.botchecker.BotTrapperTrapFile;
import com.bc.web.botchecker.BotTrapperVoid;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.configuration.Configuration;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author poshjosh
 */
public class BotFilter extends com.bc.web.core.filters.BotFilter {

    @Override
    protected BotChecker createBotChecker(HttpServletRequest request) {
        
        final BotChecker botChecker;
        
        final AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        final Configuration configuration = appContext.getConfiguration();
        
        final String cacheDir = configuration.getString(ConfigNames.BOTFILTER_CACHEDIR, null);
        
        if(cacheDir == null || cacheDir.isEmpty()) {
            
            botChecker = new BotCheckerVoid();
            
        }else{
        
            final BotCheckerImpl.BotTrapper botTrapper;
            
            final String trapFilename = configuration.getString(ConfigNames.BOTFILTER_TRAP_FILENAME, null);
        
            if(trapFilename == null || trapFilename.isEmpty()) {
                
                botTrapper = new BotTrapperVoid();
                
            }else{
                
                botTrapper = new BotTrapperTrapFile(trapFilename);
            }
        
            final boolean debug = appContext.getConfiguration().getBoolean(ConfigNames.DEBUG, false);
            
            final JSONParser parser = new JSONParser();
            final BotDiscCacheJson.Function<Reader, Object> readJson = new BotDiscCacheJson.Function<Reader, Object>() {
                @Override
                public Object apply(Reader reader) {
                    try{
                        return parser.parse(reader);
                    }catch(IOException | ParseException e){
                        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error reading json", e);
                        return new ArrayList();
                    }
                }
            };
            
            final JsonBuilder builder = new JsonBuilder(true);
            final BotDiscCacheJson.BiConsumer<Writer, Object> writeJson = new BotDiscCacheJson.BiConsumer<Writer, Object>() {
                @Override
                public void accept(Writer writer, Object object) {
                    try{
                        builder.appendJSONString(object, writer);
                    }catch(Exception e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error writing json", e);
                    }
                }
            };

            final BotCheckerImpl.BotCache botCache = debug ?
                    new BotDiscCacheJsonDevMode(readJson, writeJson, cacheDir, 100) :
                    new BotDiscCacheJson(readJson, writeJson, cacheDir, 100);

            if(debug) {

                botChecker = new BotCheckerDevMode(botTrapper, botCache);
//                botChecker = new BotCheckerVoid();

            }else{

                botChecker = new BotCheckerImpl(botTrapper, botCache);
//                botChecker = new BotCheckerVoid();
            }
        }
            
        return botChecker;
    }
}
/**
 * 
    @Override
    protected BotChecker createBotChecker(HttpServletRequest request) {
        
        final BotChecker botChecker;
        
        final AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        final Configuration configuration = appContext.getConfiguration();
        
        final String cacheDir = configuration.getString(ConfigNames.BOTFILTER_CACHEDIR, null);
        
        if(cacheDir == null || cacheDir.isEmpty()) {
            
            botChecker = BotChecker.voidInstance();
            
        }else{
        
            final BotCheckerImpl.BotTrapper botTrapper;
            
            final String trapFilename = configuration.getString(ConfigNames.BOTFILTER_TRAP_FILENAME, null);
        
            if(trapFilename == null || trapFilename.isEmpty()) {
                
                botTrapper = BotTrapper.voidInstance();
                
            }else{
                
                botTrapper = new BotTrapperTrapFile(trapFilename);
            }
        
            final boolean debug = appContext.getConfiguration().getBoolean(ConfigNames.DEBUG, false);
            
            final JSONParser parser = new JSONParser();
            final Function<Reader, Object> readJson = (reader) -> {
		try{
                    return parser.parse(reader);
		}catch(IOException | ParseException e){
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error reading json", e);
                    return new ArrayList();
		}
            };
            
            final JsonBuilder builder = new JsonBuilder(true);
            final BiConsumer<Writer, Object> writeJson = (writer, object) -> {
                try{
                    builder.appendJSONString(object, writer);
                }catch(Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error writing json", e);
                }
            };

            final BotCheckerImpl.BotCache botCache = debug ?
                    new BotDiscCacheJsonDevMode(readJson, writeJson, cacheDir, 100) :
                    new BotDiscCacheJson(readJson, writeJson, cacheDir, 100);

            if(debug) {

                botChecker = new BotCheckerDevMode(botTrapper, botCache);

            }else{

                botChecker = new BotCheckerImpl(botTrapper, botCache);
            }
        }
            
        return botChecker;
    }
 * 
 */