package com.idisc.web.servlets.handlers;

import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import com.idisc.web.exceptions.ValidationException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Josh
 */
public abstract class AbstractUpdateValues extends BaseRequestHandler<Map> {

    public abstract String [] getNames();

    protected abstract int execute(String name, Object values, Installation installation) throws Exception;
    
    @Override
    public boolean isProtected() {
        // We need this data, so no need to be protective
        return false;
    }
    
    protected Object getValues(String name, HttpServletRequest request) throws ValidationException {
        String str = request.getParameter(name);
        if(str == null || str.isEmpty()) {
            throw new ValidationException("Required parameter "+name+" is missing");
        }
        JSONParser parser = new JSONParser();
        try{
            Object json = parser.parse(str); 
            return json;
        }catch(ParseException e) {
            throw new ValidationException("Invalid format for required parameter: "+name, e);
        }
    }

    @Override
    public Map execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException {
        
        User user = this.findUser(request, response);
        
        // Create the installation record if it doesn't exist
        boolean create = true;
        Installation installation = AppInstallation.getEntity(request, user, create);
        
        String [] names = this.getNames();
        
        Map output = new HashMap(3, 1.0f);
        try{
            for(String name:names) {

                Object values = this.getValues(name, request);

                int updated = this.execute(name, values, installation); 
                
                output.put(name, updated);
            }
        }catch(ValidationException e) {
            
            throw e;
            
        }catch(Exception e) {
            
            throw new ServletException("Error updating values", e);
        }
        
        return output;
    }
}


