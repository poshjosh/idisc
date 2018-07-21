/*
 * Copyright 2016 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idisc.web.filters;

import com.bc.web.core.filters.BaseFilter;
import com.idisc.web.exceptions.LoginException;
import com.idisc.web.servlets.handlers.request.SessionUserHandler;
import com.idisc.web.servlets.handlers.request.SessionUserHandlerImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Josh
 */
public class LoginFilter extends BaseFilter {
    
    private final SessionUserHandler sessionUserHandler;
    
    public LoginFilter() { 
        sessionUserHandler = new SessionUserHandlerImpl();
    }    
    
    @Override
    protected boolean doBeforeProcessing(
            HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {

        if (!sessionUserHandler.isLoggedIn(request)) {
            
            sessionUserHandler.tryLogin(request); 
            
            if (!sessionUserHandler.isLoggedIn(request)) {
                
                throw new LoginException("Login required");
            }
        }
        
        return true;
    }    
 }
