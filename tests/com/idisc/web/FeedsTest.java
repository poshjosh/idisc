package com.idisc.web;

import com.idisc.web.servlets.ServiceController;
import com.idisc.web.servlets.handlers.request.RequestHandlerProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author Josh
 */
public class FeedsTest extends LoginTest {

    public FeedsTest() { }

    @Override
    public RequestHandlerProvider createRequestHandlerProvider() {
      return new ServiceController();
    }
    
    public Map<String, String> getParameters() {
        Map<String, String> params = new HashMap();
        params.put("req", "feeds");
        params.put("limit", "20");
        params.put("tidy", "false");
        return params;
    }
    
    @Test
    public void test() {
        
        String from = "/feed/455167.jsp";
        String to = "";
        Map<String, String> params = this.getParameters();
        
        final int numberOfRequests = 3;
        
        final int intervalMillis = 1000;
        
        final long maxWait = 120 * numberOfRequests;
        
        final TimeUnit timeUnit = TimeUnit.SECONDS;
        
        this.execute(from, params, to, numberOfRequests, intervalMillis, maxWait, timeUnit);
    }
}
