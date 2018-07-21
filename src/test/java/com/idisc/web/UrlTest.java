package com.idisc.web;

import com.bc.io.CharFileIO;
import com.bc.net.impl.RequestBuilderImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import com.bc.net.RequestBuilder;

/**
 * @author Josh
 */
public class UrlTest {
    
    private final String baseUrl = "http://localhost:8080";
    
    @Test
    public void testAddExtractedemails() throws MalformedURLException, IOException {
        
        String urlString = baseUrl+"/idisc/addextractedemails";
        
        Map params = new HashMap();
        params.put("installationkey", UUID.nameUUIDFromBytes("posh.bc@gmail.com".getBytes()));
        params.put("extractedemails", "{\"posh.bc@gmail.com\":\"1kjvdul-\"}");
        Date date = new Date();
        params.put("firstinstallationdate", date.getTime());
        params.put("lastinstallationdate", date.getTime());
        
        this.testLink(urlString, params);
    }
    
    public void testLink(String urlString, Map<String, String> params) 
            throws MalformedURLException, IOException {
        final RequestBuilder connMgr = new RequestBuilderImpl();
        final InputStream in = connMgr
                .formContentType("UTF-8")
                .url(new URL(urlString))
                .body()
                .params(params, true)
                .back().getInputStream();
        CharSequence cs = new CharFileIO().readChars(in);
System.out.println(cs);
    }
}
