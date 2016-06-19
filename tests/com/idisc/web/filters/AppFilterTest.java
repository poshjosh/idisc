package com.idisc.web.filters;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Josh
 */
public class AppFilterTest {
    
    public AppFilterTest() { }
    
    @BeforeClass
    public static void setUpClass() { }
    
    @AfterClass
    public static void tearDownClass() { }
    
    @Before
    public void setUp() { }
    
    @After
    public void tearDown() { }
    
    /**
     * Test of isRequestFromOrToAWebPage method, of class AppFilter.
     */
    @Test
    public void testIsRequestFromOrToAWebPage_String_String() {
// Referer: http://localhost:8080/idisc/feed/455167.jsp, Request URI: /idisc/resources/looseboxes.js
// Referer: http://localhost:8080/idisc/feed/455167.jsp, Request URI: /idisc/slider/data1/images/banner_social.png
        System.out.println("isRequestFromOrToAWebPage");
        
        String referer = "http://localhost:8080/idisc/feed/455167.jsp";
        String uri = "/idisc/resources/looseboxes.js";
        AppFilter instance = new AppFilter();
        boolean expResult = true;
        boolean result = instance.isRequestFromOrToAWebPage(referer, uri);
System.out.println(this.getClass().getName()+". Expected: "+expResult+", found: "+result);        
        assertEquals(expResult, result);
    }
}
