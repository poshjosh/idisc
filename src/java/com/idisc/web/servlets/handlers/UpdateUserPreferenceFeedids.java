package com.idisc.web.servlets.handlers;

import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Josh
 */
public abstract class UpdateUserPreferenceFeedids extends BaseRequestHandler<List> {

    public abstract String getRequestParameterName();

    /**
     * 
     * @param name The name of the request parameter used to convey the feedids to be added/removed
     * @param values The feedids to be added/removed from user preferences
     * @param installation The installation requesting the updates
     * @return The list of feedids for which add/remove failed
     * @throws Exception 
     */
    protected abstract List execute(String name, JSONArray values, Installation installation) throws Exception;
    
    @Override
    public boolean isProtected() {
        return true;
    }
    
    protected JSONArray getValues(String name, HttpServletRequest request) throws ServletException {
        String str = request.getParameter(name);
        if(str == null || str.isEmpty()) {
            throw new ServletException("Required parameter "+name+" is missing");
        }
        JSONParser parser = new JSONParser();
        try{
            JSONArray json = (JSONArray)parser.parse(str); 
            return json;
        }catch(ParseException e) {
            throw new ServletException("Invalid format for required parameter: "+name, e);
        }
    }

    @Override
    public List execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException {
        
        User user = this.findUser(request, response);
        
        // Create the installation record if it doesn't exist
        boolean create = true;
        Installation installation = AppInstallation.getEntity(request, user, create);
        
        String name = this.getRequestParameterName();

        List output;
        try{
            
            JSONArray values = this.getValues(name, request);

            output = this.execute(name, values, installation); 
            
        }catch(Exception e) {
            
            output = null;
            
            throw new ServletException("Error updating user preference feedids", e);
        }
        
        return output;
    }
}


