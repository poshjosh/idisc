package com.idisc.web.servlets.handlers.request;

import com.idisc.web.LoginBase;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import org.junit.Test;

/**
 * @author Josh
 */
public class FeedsTest extends LoginBase {

    public FeedsTest() { }

    @Test
    public void test() throws ServletException, IOException {
        
        this.execute("/index.jsp", "?installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20&format=text/json&tidy=true", "/feeds");
    }
    
//    @Test
    public void test2() {
        
        String from = "/feed/455167.jsp";
        String to = "";
        Map<String, String> params = new HashMap();
        params.put("req", "feeds");
        params.put("limit", "20");
        params.put("tidy", "false");
        
        final int numberOfRequests = 3;
        
        final int intervalMillis = 1000;
        
        final long maxWait = 120 * numberOfRequests;
        
        final TimeUnit timeUnit = TimeUnit.SECONDS;
        
        this.execute(from, params, to, numberOfRequests, intervalMillis, maxWait, timeUnit);
    }
}
