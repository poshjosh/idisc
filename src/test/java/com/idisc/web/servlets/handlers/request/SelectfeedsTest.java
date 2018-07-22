package com.idisc.web.servlets.handlers.request;

import com.bc.webapptest.HttpServletRequestImpl;
import com.idisc.web.LoginBase;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Test;

/**
 * @author Josh
 */
public class SelectfeedsTest extends LoginBase {
    
    public SelectfeedsTest() { }
    
    @Test
    public void test() throws ServletException, IOException {
        final String from = "/index.jsp";
        final String searchTerm = "tiwa+savage";
        final String query = "?query="+searchTerm+"&installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20";
//        final String to = "/selectfeeds";
        final String to = "/searchresults";
        this.execute(from, query, to);
    }
    
//    @Test
    public void testExecute() throws ServletException, IOException {
        HttpServletRequestImpl request = this.createRequest();
        final String query = "tiwa+savage";
        request.from("/index.jsp").with("?query="+query+"&installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20").to("/selectfeeds");
        Selectfeeds instance = new Selectfeeds();
        
//        List<Feed> selected = instance.execute(request);
//this.log("Selected %d feeds for %s", selected==null?null:selected.size(), query);
    }
}
