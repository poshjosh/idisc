package com.idisc.web;

import com.authsvc.client.AuthSvcSession;
import com.bc.util.concurrent.NamedThreadFactory;
import com.bc.webapptest.HttpServletRequestImpl;
import com.bc.webapptest.HttpServletResponseImpl;
import com.bc.webapptest.WebappSetup;
import com.idisc.web.servlets.handlers.ServiceController;
import com.idisc.web.servlets.handlers.ServiceControllerImpl;
import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.request.RequestHandlerProvider;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 * @(#)Bcwebapptest.java   08-May-2015 19:42:40
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class TestBase extends WebappSetup {
    
    private static volatile int taskCount;
    
    private final RequestHandlerProvider requestHandlerProvider;
    
    protected TestBase() {
        super(
                new com.idisc.web.listeners.ServletContextListener(),
                "http://localhost:8080",
                System.getProperty("user.home")+"/Documents/NetBeansProjects/idisc/web",
                "/idisc");
        requestHandlerProvider = TestBase.this.createRequestHandlerProvider();
    }
    
    public Map<String, String> getDefaultOutputParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("installationid", "2");
        parameters.put("installationkey", "abdb33ee-a09e-4d7d-b861-311ee7061325");
        parameters.put("screenname", "user_2");
        parameters.put("format", "text/json");
        parameters.put("versionCode", "20");
        parameters.put("tidy", "true");
        return parameters;
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    @Before
    public void setUp() { 
//log("0 x 0 x 0 x 0 x 0 x 0 x 0 x 0 x SETTING UP 0 x 0 x 0 x 0 x 0 x 0 x 0 x 0 x ");
        super.contextInitialized();
    }
    @After
    public void tearDown() { 
//log("0 x 0 x 0 x 0 x 0 x 0 x 0 x 0 x TEARING DOWN 0 x 0 x 0 x 0 x 0 x 0 x 0 x 0 x ");        
        super.contextDestroyed();
    }
    
    @AfterClass
    public static void tearDownClass() { }
    
    public RequestHandlerProvider createRequestHandlerProvider() {
        return new ServiceControllerImpl();        
    }
    
    public synchronized AuthSvcSession waitForAuthSession(AppContext appContext, long maxWait, TimeUnit timeUnit) throws InterruptedException {
        AuthSvcSession authSess;
        final long maxWaitMillis = timeUnit.toMillis(maxWait); 
        final int interval = 1000;
        int sleepTime = 0;
        try{
            do{
                authSess = appContext.getAuthSvcSession();
                this.wait(interval);
                sleepTime += interval;
            }while(authSess == null && sleepTime < maxWaitMillis);
        }finally{
            this.notifyAll();
        }
        return authSess;
    }    
        
    public RequestHandlerProvider getRequestHandlerProvider() {
        return this.requestHandlerProvider; 
    }

    public void execute(
            final String from, 
            final Map<String, String> params,
            final String to,
            final int numberOfRequests,
            final int submissionIntervalMillis,
            final long maxTime,
            final TimeUnit timeUnit) {
        
        this.execute(this.requestHandlerProvider, from, params, to, 
                numberOfRequests, submissionIntervalMillis, maxTime, timeUnit);
    }
    
    public void execute(
            final RequestHandlerProvider provider, 
            final String from, 
            final Map<String, String> params,
            final String to,
            final int numberOfRequests,
            final int submissionIntervalMillis,
            final long maxTime,
            final TimeUnit timeUnit) {
    
        final List<Callable> callables = new ArrayList<>(numberOfRequests);
        final List<Future> futures = new ArrayList<>(numberOfRequests);
        
        ExecutorService executorService = Executors.newCachedThreadPool(
                new NamedThreadFactory("TestBase_ThreadPool"));
        
        for(int i=0; i<numberOfRequests; i++) {
            
            Callable callable = this.getCallable(provider, from, params, to);
            
            Future future = executorService.submit(callable);
            
            callables.add(callable);
            
            futures.add(future);
            
            try{
                Thread.sleep(submissionIntervalMillis);
            }catch(InterruptedException e) {
                log(e);
            }
        }
        
        com.bc.util.Util.shutdownAndAwaitTermination(executorService, maxTime, timeUnit);
        
        for(int i=0; i<futures.size(); i++) {
            
            try{
                
                Callable callable = callables.get(i);
                
                Future future = futures.get(i);
                
                final Object result = future.get();
            
                log("\n\n" + callable + " RESULTS: " + result);
                
            }catch(InterruptedException | ExecutionException e) {
                
                log(e);
            }
        }
    }

    public Callable getCallable(final String from, final Map<String, String> params) {
        
        return this.getCallable(this.requestHandlerProvider, from, params);
    }
    
    public Callable getCallable(final RequestHandlerProvider provider, final String from, final Map<String, String> params) {
        
        return this.getCallable(provider, from, params, "");
    }

    public Callable getCallable(final String from, final Map<String, String> params, final String to) {
        
        return this.getCallable(requestHandlerProvider, from, params, to);
    }
        
    public Callable getCallable(
            final RequestHandlerProvider provider, final String from, final Map<String, String> params, final String to) {
        
//log("Provider: %s", provider);

        Callable callable = new Callable() {
            
            private final int taskId = ++taskCount;
            
            @Override
            public Object call() throws Exception {
                log("====================================== Started "+this);
                try{
                    return TestBase.this.execute(provider, from, params, to);
                }finally{
                    log("====================================== Completed "+this);
                }
            }

            @Override
            public String toString() {
//                return "Callable to handle requests from: "+from+", to: "+to+", parameters: "+params;
                return "Callable["+taskId+']';
            }
        };
        
        return callable;
    }
    
    public Runnable getRunnable(final String from, final Map<String, String> params) {
        
        return this.getRunnable(this.requestHandlerProvider, from, params);
    }
    
    public Runnable getRunnable(final RequestHandlerProvider provider, final String from, final Map<String, String> params) {
        
        return this.getRunnable(provider, from, params, "");
    }

    public Runnable getRunnable(final String from, final Map<String, String> params, final String to) {
        
        return this.getRunnable(requestHandlerProvider, from, params, to);
    }
        
    public Runnable getRunnable(
            final RequestHandlerProvider provider, final String from, final Map<String, String> params, final String to) {
        
//log("Provider: %s", provider);

        Runnable runnable = new Runnable() {
            
            private final int taskId = ++taskCount;
            
            @Override
            public void run() {
                log("====================================== Started "+this);
                try{
                    TestBase.this.execute(provider, from, params, to);
                }catch(ServletException | IOException | RuntimeException e) {
                    log("====================================== Error executing "+this, e);
                }finally{
                    log("====================================== Completed "+this);
                }
            }

            @Override
            public String toString() {
//                return "Runnable to handle requests from: "+from+", to: "+to+", parameters: "+params;
                return "Runnable["+taskId+']';
            }
        };
        
        return runnable;
    }

    public Object execute(
            final String from, 
            final String query, 
            final String to) throws ServletException, IOException {
        
        return this.execute(this.requestHandlerProvider, from, query, to);
    }
    
    public Object execute(
            final RequestHandlerProvider provider, 
            final String from, 
            final String query, 
            final String to) throws ServletException, IOException {
        
        final HttpServletRequestImpl request = createRequest(getSession(true));

        request.from(from).with(query).to(to);
        
        return this.execute(provider, request);
    }
    
    public Object execute(final String from, 
            final Map<String, String> params, 
            final String to) throws ServletException, IOException {
        
        return this.execute(this.requestHandlerProvider, from, params, to);
    }
    
    public Object execute(
            final RequestHandlerProvider provider, 
            final String from, 
            final Map<String, String> params, 
            final String to) throws ServletException, IOException {
        
        final HttpServletRequestImpl request = createRequest(getSession(true));

        request.from(from).with(params).to(to);
        
        return this.execute(provider, request);
    }

    public Object execute(
            final RequestHandlerProvider provider, 
            final HttpServletRequest request) throws ServletException, IOException {
        
        HttpServletResponseImpl response = new HttpServletResponseImpl();

        RequestHandler rh = provider.getRequestHandler((HttpServletRequest)request);
        
        final String name = provider.getRequestHandlerNames(request)[0];

        try{

            ((ServiceController)provider).process(request, response, true);

//                    ((ServiceController)provider).process(rh, request, response, name, true);

        }catch(ClassCastException e) {

            Object value = rh.processRequest(request);

log("\n\n INITIAL RESULTS: "+value); 
            ResponseHandler responseHandler = rh.getResponseHandler(request);

            responseHandler.processAndSendResponse(request, response, name, value);
        }

        StringBuffer output = response.getBuffer();
        
log("\n\n FINAL RESULTS: "+output);         
        
        return output;
    }
}
