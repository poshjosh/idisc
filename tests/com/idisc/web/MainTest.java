package com.idisc.web;

import com.bc.util.Util;
import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Site;
import com.idisc.pu.entities.Sitetype;
import com.idisc.pu.entities.Feed;
import com.idisc.web.servlets.handlers.Selectfeeds;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josh
 */
public class MainTest {
    
    public MainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
System.out.println("main");

        String [] taskNames = {"A", "B", "C"};
        List<Callable<Object>> tasks = new ArrayList<>(taskNames.length);
        for(String name:taskNames) {
            Callable<Object> task = this.getCallable(name, (int)(Math.random() * 10));
            tasks.add(task);
        }
        
        ExecutorService es = Executors.newFixedThreadPool(taskNames.length == 1 ? 1 : taskNames.length -1);
        
        long timeout = 5;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        
        try{
            List<Future<Object>> futures = es.invokeAll(tasks, timeout, timeUnit);
            Util.shutdownAndAwaitTermination(es, timeout, timeUnit);
        }catch(Exception e) {
            e.printStackTrace();
        }
        
if(true) {
    return;
}        
        
        try{

long mb4 = Runtime.getRuntime().freeMemory();
long tb4 = System.currentTimeMillis();

            for(int i=0; i<1000; i++) {
                Feed feed = new Feed();
                feed.setAuthor("Chinomso Bassey Ikwuagwu");
                feed.setCategories("News,sports,politics,business,international");
                StringBuilder builder = new StringBuilder();
                for(int j=0; j<1000; j++) {
                    builder.append("This is a line in the simple sample content I have decided to use for my memory test").append(j).append("\n");
                }
                feed.setContent(builder.toString());
                feed.setDescription("This is the desccription of the feeds, and you need to know we mean business");
                feed.setFeeddate(new Date());
                feed.setFeedid(i);
                feed.setImageurl("http://www.looseboxes.com/thisistheimage.jpg");
                feed.setKeywords("feed keywords feed keywords");
                Site site = new Site();
                site.setDatecreated(new Date());
                site.setIconurl("http://wlwlslslnvslnslnslnslnsslnslnssln");
                site.setSite("SampleSiteName"+i);
                site.setSiteid(i);
                Sitetype sitetype = new Sitetype();
                sitetype.setSitetype("SampleSiteType_"+i);
                sitetype.setSitetypeid((short)i);
                site.setSitetypeid(sitetype);
                feed.setSiteid(site);
                feed.setTimemodified(new Date());
                feed.setUrl("http://www.llslslslslslslslslslslslslslslslslslsslslslslslsl");
            }
            
System.out.println("One thousand feeds in memory, Consumed memory: "+(mb4-Runtime.getRuntime().freeMemory())+", time: "+(System.currentTimeMillis()-tb4));            
            
Thread.sleep(5000);

System.out.println("After five seconds sleep, Consumed memory: "+(mb4-Runtime.getRuntime().freeMemory()));

Thread.sleep(5000);

System.out.println("After five seconds sleep, Consumed memory: "+(mb4-Runtime.getRuntime().freeMemory()));

if(true) {
    return;
}
            IdiscApp.getInstance().init();
            
            List<Feed> selected = new Selectfeeds().select(null, null, 0, 1000);
        
XLogger.getInstance().log(Level.INFO, "Selected: {0} feeds", MainTest.class, selected==null?null:selected.size());

        }catch(Exception e) {
            
            e.printStackTrace();
        }
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    private Callable<Object> getCallable(final String name, final long length) {
System.out.println("Task, name: "+name+", length: "+length+" seconds.");
        return new Callable() {
            @Override
            public synchronized Object call() throws Exception {
                int i = 0;
                for(; i<length; i++) {
                    try{
                        wait(1000);
                    }finally{
                        notifyAll();
                    }
                    System.out.println("Task: "+name+" ... ... ... "+i);
                }
                return i;
            }
        };
    }
}
