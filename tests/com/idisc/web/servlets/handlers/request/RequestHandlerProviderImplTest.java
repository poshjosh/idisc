package com.idisc.web.servlets.handlers.request;

import com.bc.webapptest.HttpServletRequestImpl;
import com.idisc.web.LoginBase;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Josh
 */
public class RequestHandlerProviderImplTest extends LoginBase {
    
    public RequestHandlerProviderImplTest() {  }
    
    @BeforeClass
    public static void setUpClass() { }
    
    @AfterClass
    public static void tearDownClass() { }
    
    /**
     * Test of getRequestHandlerParamName method, of class RequestHandlerProviderImpl.
     */
    @Test
    public void testGetRequestHandlerParamName() {
        System.out.println("getRequestHandlerParamName");
        HttpServletRequestImpl request = this.createRequest();
        request.from("/index.jsp").with(Collections.singletonMap("feedid", "455167")).to("/feed");
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        String expResult = "feed";
        String result = instance.getRequestHandlerParamName(request);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRequestHandlerParamNames method, of class RequestHandlerProviderImpl.
     */
    @Test
    public void testGetRequestHandlerParamNames() {
        System.out.println("getRequestHandlerParamNames");
        HttpServletRequestImpl request = this.createRequest();
        request.from("/index.jsp").with(Collections.singletonMap("feedid", "455167")).to("/feed");
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        String[] expResult = {"feed"};
        String[] result = instance.getRequestHandlerParamNames(request);
        assertArrayEquals(expResult, result);
    }
   
    @Test
    public void testGetRequestHandlerParamNames2() {
        System.out.println("getRequestHandlerParamNames2");
// Referer: http://localhost:8080/idisc/login.jsp, Request URI: /idisc/login
        final Map<String, String> loginParameters = new HashMap<>();
        loginParameters.put("emailaddress", "posh.bc@gmail.com");
        loginParameters.put("password", "1kjvdul-");
        System.out.println("getRequestHandlerParamNames");
        HttpServletRequestImpl request = this.createRequest();
        request.from("/login.jsp").with(loginParameters).to("/login");
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        String[] expResult = {"login"};
        String[] result = instance.getRequestHandlerParamNames(request);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testGetRequestHandlerParamNames3() {
        System.out.println("getRequestHandlerParamNames3");
        HttpServletRequestImpl request = this.createRequest();
        request.from("/index.jsp").with("?req=aliasesforcategories&req=aliasesforcontent&req=appproperties&tidy=true").to("/getmultipleresults");
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        String[] expResult = {"aliasesforcategories", "aliasesforcontent", "appproperties"};
this.log("Expected: %s", Arrays.toString(expResult));
        String[] result = instance.getRequestHandlerParamNames(request);
this.log("   Found: %s", result==null?null:Arrays.toString(result));        
        assertArrayEquals(expResult, result);
    }
    
    /**
     * Test of getRequestHandler method, of class RequestHandlerProviderImpl.
     */
    @Test
    public void testGetRequestHandler_HttpServletRequest() {
        System.out.println("getRequestHandler(HttpServletRequest)");
        HttpServletRequestImpl request = this.createRequest();
        request.from("/index.jsp").with(Collections.singletonMap("feedid", "455167")).to("/feed");
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        RequestHandler expResult = new com.idisc.web.servlets.handlers.request.Feed();
        RequestHandler result = instance.getRequestHandler(request);
        assertEquals(expResult.getClass(), result.getClass());
    }

    /**
     * Test of getRequestHandler method, of class RequestHandlerProviderImpl.
     */
    @Test
    public void testGetRequestHandler_String() {
        System.out.println("getRequestHandler(String)");
        String name = "newcomment";
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        RequestHandler expResult = new com.idisc.web.servlets.handlers.request.Newcomment();
        RequestHandler result = instance.getRequestHandler(name);
        assertEquals(expResult.getClass(), result.getClass());
    }

    /**
     * Test of getRequestHandler method, of class RequestHandlerProviderImpl.
     */
    @Test
    public void testGetRequestHandler_Class() {
        System.out.println("getRequestHandler(Class)");
        Class aClass = com.idisc.web.servlets.handlers.request.Feeds.class;
        RequestHandlerProviderImpl instance = new RequestHandlerProviderImpl();
        RequestHandler expResult = new com.idisc.web.servlets.handlers.request.Feeds();
        RequestHandler result = instance.getRequestHandler(aClass);
        assertEquals(expResult.getClass(), result.getClass());
    }
}
