package com.idisc.web;

import com.bc.io.CharFileIO;
import com.bc.net.ConnectionManager;
import com.bc.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author Josh
 */
public class ThroughputTest {
  
    @Test
    public void test() throws MalformedURLException {
        
        String [] urlStrings = {
            "http://localhost:8080/idisc/feeds?installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20&format=text/json&tidy=true",
            "http://localhost:8080/idisc/feed/495879_House_probes_members_over_US_sex_scandal_.jsp",
            "http://localhost:8080/idisc/feed?feedid=495876&format=text/json",
            "http://localhost:8080/idisc/feed/455167.jsp"
        };
        
        ExecutorService svc = Executors.newCachedThreadPool();
        
        int id = 0;
        
        for(String urlString:urlStrings) {
            
            Runnable task = new UrlReadTask(String.valueOf(id++), new URL(urlString));
            
            svc.submit(task);
        }
        
        Util.shutdownAndAwaitTermination(svc, 60, TimeUnit.SECONDS);
    }
    
    private class UrlReadTask implements Runnable {
        private final String id;
        private final URL url;
        private final int numberOfLoads;
        private final long intervalMillis;
        private UrlReadTask(String id, URL url) {
            this(id, url, 10, 2, TimeUnit.SECONDS);
        }
        private UrlReadTask(String id, URL url, int numberOfLoads, long interval, TimeUnit timeUnit) {
            this.id = id;
            this.url = url;
            this.numberOfLoads = numberOfLoads;
            this.intervalMillis = timeUnit.toMillis(interval);
System.out.println("Created Task("+id+") url: "+url+", numberOfLoads: "+numberOfLoads+", interval: "+intervalMillis+" milliseconds.");                                            }
        @Override
        public void run() {
            try{
                ConnectionManager connMgr = new ConnectionManager();
                connMgr.setAddCookies(true);
                connMgr.setGenerateRandomUserAgent(true);
                connMgr.setGetCookies(true);
                CharFileIO io = new CharFileIO();
                for(int i=0; i<numberOfLoads; i++) {
final long tb4 = System.currentTimeMillis();
                    try(InputStream in = connMgr.getInputStream(url)) {
                        CharSequence cs = io.readChars(in); 
System.out.println("Task("+id+")("+i+"), time spent: "+(System.currentTimeMillis()-tb4)+",read "+cs.length()+" chars");                        
                    }
                    try{
                        Thread.sleep(intervalMillis);
                    }catch(InterruptedException e) {
System.err.println("Task("+id+")("+i+"), INTERRUPTED: "+e);                          
                    }
                }
            }catch(IOException e) {
                System.err.println(e.toString());
            }
        }
    }
}
