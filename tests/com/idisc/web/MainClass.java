package com.idisc.web;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Josh
 */
public class MainClass {
    
    public static void main(String [] args) {
        FeedsTest test = new FeedsTest();
        String from = "/feed/455167.jsp";
        String to = "";
        new MainClass().execute2(test, from, test.getParameters(), to, 3);
    }
    
    private void execute(BaseTest test, String from, Map<String, String> params, String to, int count) {
        try{
            BaseTest.setUpClass();
            try{
                test.setUp();
                for(int i=0; i<count; i++) {
                    Runnable runnable = test.getRunnable(from, params, to);
                    if(i < count-1) {
                        new Thread(runnable).start();
                    }else{
                        runnable.run();
                    }
                }
            }finally{
                test.tearDown();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            BaseTest.tearDownClass();
        }
    }

    private void execute2(BaseTest test, String from, Map<String, String> params, String to, int count) {
        try{
            BaseTest.setUpClass();
            try{
                test.setUp();
                ExecutorService svc = Executors.newCachedThreadPool();
                for(int i=0; i<count; i++) {
                    Runnable runnable = test.getRunnable(from, params, to);
                    svc.submit(runnable);
                }
                com.bc.util.Util.shutdownAndAwaitTermination(svc, 500, TimeUnit.MILLISECONDS);
            }finally{
                test.tearDown();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            BaseTest.tearDownClass();
        }
    }
}
