package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.JpaContext;
import com.bc.ui.treebuilder.MapTreeBuilder;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.LoginBase;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.MutableTreeNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import com.bc.jpa.dao.BuilderForSelect;

/**
 * @author Josh
 */
public class FeedsTest extends LoginBase {

    public FeedsTest() { }

//    @Test
    public void testVersions() throws ServletException, IOException {
        
        final int width = 1000;
        final int height = 800;
        
        final int max = 100;
        
        AppContext appContext = (AppContext)this.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        JpaContext jpaContext = appContext.getIdiscApp().getJpaContext();
        
        List<Installation> resultList;
        
        try(BuilderForSelect<Installation> qb = jpaContext.getBuilderForSelect(Installation.class)) {
            
            resultList = qb.from(Installation.class).createQuery().setMaxResults(max).getResultList();
        }
        
        int i = 0;
        
        for(Installation instl:resultList) {
            
            if(i++ == max) {
                break;
            }
log("---------------Installation: "+instl);                        
            
            String query = "installationid="+instl.getInstallationid()+"&installationkey="+instl.getInstallationkey()+"&limit=20&format=text/json&tidy=true";
//            String query = "versionCode="+(i+5)+"&installationid="+instl.getInstallationid()+"&installationkey="+instl.getInstallationkey()+"&limit=20&format=text/json&tidy=true";
            
            Object jsonText = this.execute("/index.jsp", query, "/feeds");
            
            JComponent ui;
            
            if(jsonText == null) {
                JLabel label = new JLabel();
                String text = "<html><p>"+instl+"</p><p>Feeds:<br/>"+jsonText+"</p></html>";
                label.setText(text);
                ui = label;
            }else{
                
                final int toprint = 5;
                
                JSONObject jsonObj;
                try{
                    jsonObj = (JSONObject)new JSONParser().parse(jsonText.toString());
                    List feeds = (List)jsonObj.get("feeds");
                    int size = feeds.size();
                    for(int pos=size-toprint; pos<size; pos++) {
                        Map data = (Map)feeds.get(pos);
log("ID:"+data.get("feedid")+", siteid: "+data.get("siteid"));                        
                    }
                    MapTreeBuilder treeBuilder = new MapTreeBuilder();
                    MutableTreeNode treeNode = treeBuilder.build(jsonObj);
                    JTree tree = new JTree(treeNode);
                    ui = tree;
                }catch(ParseException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    JLabel label = new JLabel();
                    String text = "<html><p>"+instl+"</p><p>Feeds:<br/>"+sw.getBuffer()+"</p></html>";
                    label.setText(text);
                    ui = label;
                }
            }
            
            ui.setSize(width, height);
            JScrollPane scrolls = new JScrollPane(ui);
            scrolls.setSize(width, height);
            
//            JOptionPane.showMessageDialog(null, scrolls);
        }
    }
    
//    @Test
    public void test() throws ServletException, IOException {
        
        this.execute("/index.jsp", "installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20&format=text/json&tidy=true", "/feeds");
    }
    
    @Test
    public void testMaxLen() throws ServletException, IOException {
        
        Path path1 = Paths.get(System.getProperty("user.home"), "/Desktop/one.json");
        this.testMaxLen(1000, path1);
        
        Path path2 = Paths.get(System.getProperty("user.home"), "/Desktop/two.json");
        this.testMaxLen(32000, path2);
    }
    
    public void testMaxLen(int maxLen, Path path) throws ServletException, IOException {
        Object output = this.execute("/index.jsp", "maxlen="+maxLen+"&installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20&format=text/json&tidy=true", "/feeds");
        Files.write(path, output.toString().getBytes(), StandardOpenOption.CREATE, 
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
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
