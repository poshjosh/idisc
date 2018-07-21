package com.idisc.web.servlets.handlers;

import com.idisc.web.servlets.handlers.request.RequestHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Josh
 */
public class ServiceControllerImplTest {
    
    public ServiceControllerImplTest() { }
    
    @BeforeClass
    public static void setUpClass() { }
    
    @AfterClass
    public static void tearDownClass() { }
    
    @Before
    public void setUp() { }
    
    @After
    public void tearDown() { }

    /**
     * Test of process method, of class ServiceControllerImpl.
     */
    @Test
    public void testProcess_3args() throws Exception {
        System.out.println("process");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        boolean sendResponse = false;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        instance.process(request, response, sendResponse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processAsync method, of class ServiceControllerImpl.
     */
    @Test
    public void testProcessAsync_4args() throws Exception {
        System.out.println("processAsync");
        AsyncContext asyncContext = null;
        boolean sendResponse = false;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        instance.processAsync(asyncContext, sendResponse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process method, of class ServiceControllerImpl.
     */
    @Test
    public void testProcess_5args() throws Exception {
        System.out.println("process");
        RequestHandler requestHandler = null;
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        String paramName = "";
        boolean sendResponse = false;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        instance.process(requestHandler, request, response, paramName, sendResponse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processAsync method, of class ServiceControllerImpl.
     */
    @Test
    public void testProcessAsync_6args() throws Exception {
        System.out.println("processAsync");
        AsyncContext asyncContext = null;
        RequestHandler requestHandler = null;
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        String paramName = "";
        boolean sendResponse = false;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        instance.processAsync(asyncContext, requestHandler, request, response, paramName, sendResponse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processResponse method, of class ServiceControllerImpl.
     */
    @Test
    public void testProcessResponse() throws Exception {
        System.out.println("processResponse");
        ResponseHandler responseHandler = null;
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        String paramName = "";
        Object output = null;
        boolean sendResponse = false;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        instance.processResponse(responseHandler, request, response, paramName, output, sendResponse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processResponseAsync method, of class ServiceControllerImpl.
     */
    @Test
    public void testProcessResponseAsync() throws Exception {
        System.out.println("processResponseAsync");
        AsyncContext asyncContext = null;
        ResponseHandler responseHandler = null;
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        String paramName = "";
        Object output = null;
        boolean sendResponse = false;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        instance.processResponseAsync(asyncContext, responseHandler, request, response, paramName, output, sendResponse);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of prepareResponse method, of class ServiceControllerImpl.
     */
    @Test
    public void testPrepareResponse() throws Exception {
        System.out.println("prepareResponse");
        ResponseHandler responseHandler = null;
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        String paramName = "";
        Object output = null;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        boolean expResult = false;
        boolean result = instance.prepareResponse(responseHandler, request, response, paramName, output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstParameterName method, of class ServiceControllerImpl.
     */
    @Test
    public void testGetFirstParameterName() throws ServletException {
        System.out.println("getFirstParameterName");
        HttpServletRequest request = null;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        String expResult = "";
        String result = instance.getFirstParameterName(request);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRequestHandler method, of class ServiceControllerImpl.
     */
    @Test
    public void testGetRequestHandler() throws ServletException {
        System.out.println("getRequestHandler");
        HttpServletRequest request = null;
        ServiceControllerImpl instance = new ServiceControllerImpl();
        RequestHandler expResult = null;
        RequestHandler result = instance.getRequestHandler(request, null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
