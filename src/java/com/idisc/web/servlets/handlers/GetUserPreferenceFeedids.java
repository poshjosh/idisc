package com.idisc.web.servlets.handlers;

import com.idisc.core.User;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppInstallation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public abstract class GetUserPreferenceFeedids extends BaseRequestHandler<List> {

    public abstract List getFeedids(Installation installation);

    @Override
    public boolean isProtected() {
        return true;
    }
    
    @Override
    public List execute(
            HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {

        User user = this.getUser(request);
        
        boolean create = true;
        Installation installation = AppInstallation.getEntity(request, user, create);
        
        List output = this.getFeedids(installation);
        
        return output == null ? Collections.EMPTY_LIST : output;
    }
}
