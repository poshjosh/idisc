package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.web.WebApp;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Josh
 */
public abstract class DownloadJson extends BaseRequestHandler<Map> {

    public abstract String getFilename();
            
    @Override
    public boolean isProtected() {
        // A user doesn't have to be logged in call this servlet
        return false;
    }

    @Override
    public String getResponseFormat(HttpServletRequest request) {
        return "text/json";
    }
    
    @Override
    public Map execute(
            HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {

        Map output;

        String filename = this.getFilename();
        
        String realPath = WebApp.getInstance().getServletContext().getRealPath(filename);
        
        try(Reader r = 
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(realPath), "utf-8"))) {
        
            JSONParser parser = new JSONParser();
    
            output = (Map)parser.parse(r);
            
XLogger.getInstance().log(Level.FINE, "Read json:\n{0}", this.getClass(), output);
            
        }catch(ParseException e) {
            
            output = null;
            
            XLogger.getInstance().log(Level.WARNING, "Error parsing: "+realPath, this.getClass(), e);
        }
        
        return output == null ? Collections.emptyMap() : output;
    }
}


