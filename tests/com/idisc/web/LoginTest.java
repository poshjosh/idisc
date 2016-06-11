package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.bc.webapptest.HttpServletRequestImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * @author Josh
 */
public class LoginTest extends BaseTest {
    
    private Boolean loggedIn = Boolean.FALSE;

    public LoginTest() { }

    @Override
    public HttpServletRequestImpl createRequest(HttpSession requestsSession) {
        HttpServletRequestImpl request = super.createRequest(requestsSession);
        if(this.isNewSession(requestsSession)) {
            loggedIn = Boolean.FALSE;
        }
        return request;
    }

    public void login() throws ServletException, IOException, InterruptedException, ExecutionException {
        
        this.log(this.getSession(true));
    }
    
    public void login(HttpSession session) 
            throws ServletException, IOException, InterruptedException, ExecutionException {
        
        final HttpServletRequestImpl request = this.createRequest(session);

        login(request);
    }
    
    public void login(HttpServletRequestImpl request) 
            throws ServletException, IOException, InterruptedException, ExecutionException {
        
        if(loggedIn) {
            throw new ServletException("Already logged in");
        }
        
        final long maxWait = 120;
        final TimeUnit timeUnit = TimeUnit.SECONDS;
        
log("... ... ... ... ... ... ...Waiting for "+AuthSvcSession.class.getName());        
        AuthSvcSession authSess = this.waitForAuthSession(maxWait, timeUnit);
log("... ... ... ... ... ..Done waiting for "+AuthSvcSession.class.getName());

        if(authSess == null) {
            throw new ServletException("Failed to initialize "+AuthSvcSession.class.getName()+" even after waiting for "+maxWait+" "+timeUnit);
        }
        
        try{
            loggedIn = this.getDefaultLoginTask().call();
        }catch(Exception e) {
            throw new ExecutionException(e);
        }
    }

    public Callable<Boolean> getDefaultLoginTask() {
        return this.getDefaultLoginTask(this.getSession(true));
    }
     
    public Callable<Boolean> getDefaultLoginTask(HttpSession session) {
                   
        final HttpServletRequestImpl request = this.createRequest(session);
        
        return this.getDefaultLoginTask(request);
    }    

    public Callable<Boolean> getDefaultLoginTask(final HttpServletRequestImpl request) {
        
// Referer: http://localhost:8080/idisc/login.jsp, Request URI: /idisc/login
        String from = "/login.jsp";
        String to = "/login";
        final Map<String, String> loginParameters = new HashMap<>();
        loginParameters.put("emailAddress", "posh.bc@gmail.com");
        loginParameters.put("emailaddress", "posh.bc@gmail.com");
        loginParameters.put("password", "1kjvdul-");
        Callable callable = this.getCallable(from, loginParameters, to);
        return callable;
    }
    
    public boolean isLoggedIn() {
        return loggedIn == null ? false : loggedIn;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }
}