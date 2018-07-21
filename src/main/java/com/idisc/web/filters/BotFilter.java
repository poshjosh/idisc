package com.idisc.web.filters;

import com.bc.util.JsonBuilder;
import com.bc.web.botchecker.BotChecker;
import com.bc.web.botchecker.BotCheckerDiscCacheBuilder;
import com.bc.web.botchecker.GetBotCheckerFromRequest;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author poshjosh
 */
public class BotFilter extends com.bc.web.core.filters.BaseFilter {
    
    private transient static final Logger logger = Logger.getLogger(BotFilter.class.getName());

    private static final class CreateBotChecker 
            implements Function<ServletRequest, BotChecker>, Serializable {
    
        @Override
        public BotChecker apply(ServletRequest request) {

            final AppContext appContext = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);

            final Configuration configuration = appContext.getConfiguration();

            final String cacheDir = configuration.getString(ConfigNames.BOTFILTER_CACHEDIR, null);
            final String trapFilename = configuration.getString(ConfigNames.BOTFILTER_TRAP_FILENAME, null);
            final boolean debug = configuration.getBoolean(ConfigNames.DEBUG, false);

            final JSONParser parser = new JSONParser();
            final Function<Reader, Object> jsonReader = (reader) -> {
                try{
                    return parser.parse(reader);
                }catch(IOException | ParseException e){
                    logger.log(Level.WARNING, "Error reading json", e);
                    return new ArrayList();
                }
            };

            final JsonBuilder builder = new JsonBuilder(true);
            final BiConsumer<Writer, Object> jsonWriter = (writer, object) -> {
                try{
                    builder.appendJSONString(object, writer);
                }catch(Exception e) {
                    logger.log(Level.WARNING, "Error writing json", e);
                }
            };
            
            final BotChecker botChecker = new BotCheckerDiscCacheBuilder()
                    .cacheDir(cacheDir)
                    .trapFilename(trapFilename)
//                    .jsonReader(jsonReader)
//                    .jsonWriter(jsonWriter)
                    .debug(debug).build();

            return botChecker;
        }
    }
    
    private final Function<ServletRequest, BotChecker> botcheckerProvider;

    public BotFilter() {
        this(new GetBotCheckerFromRequest(new CreateBotChecker()));
    }
    
    public BotFilter(Function<ServletRequest, BotChecker> provider) {
        this.botcheckerProvider = Objects.requireNonNull(provider);
    }

    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {

        logger.log(Level.FINER, "#doBeforeProcessing");
        
        boolean mayProceed;
        try{
            
            final BotChecker botChecker = this.botcheckerProvider.apply(request);

            mayProceed = !botChecker.isFromBot(request, response);

            logger.log(Level.FINER, "#doBeforeProcessing. Is from bot: {0}", mayProceed);
            
        }catch(RuntimeException | IOException | ServletException e) {
            
            logger.log(Level.WARNING, "", e);
            
            mayProceed = true;
        }
        
        return mayProceed;
    }
}
