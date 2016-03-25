package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.Updateusersitehitcounts;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * @(#)IdiscUpdateTask.java   05-May-2015 18:56:39
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class IdiscUpdateTask extends com.idisc.core.FeedUpdateTask {

    private boolean multipleProcesses;
    
    private volatile int completed;
    
    @Override
    public void run() {
        if(this.isMultipleProcesses()) {
            this.runMultipleProcesses();
        }else{
            this.runSingleProcess();
        }
    }
    
    public int getInterTaskSpacingMinutes() {
        return 2;
    }
    
    public int getFeedCycleIntervalMinutes() {
        return WebApp.getInstance().getConfiguration().getInt(AppProperties.FEED_CYCLE_INTERVAL, 15);
    }
    
    public int getTimeAvailableMinutes() {
        return this.getFeedCycleIntervalMinutes() - this.getInterTaskSpacingMinutes();
    }

    public void runMultipleProcesses() {
        
        try{
            
            final Runnable [] tasks = this.getTasks();
            
            final ExecutorService svc = Executors.newFixedThreadPool(tasks.length);
            
            final long timeAvailableMillis = TimeUnit.MINUTES.toMillis(this.getTimeAvailableMinutes());
            
            final Thread curr = Thread.currentThread();
            
            Thread interruptor = new Thread(){
                long startTimeMillis;
                @Override
                public void run() {
                    startTimeMillis = System.currentTimeMillis();
                    while(true) {
                        if(this.isCompleted() || 
                                curr.isInterrupted() ||
                                this.isExpired()) {
                            this.shutDown();
                            break;
                        }
                    }
                }
                private void shutDown() {
                    if(!svc.isShutdown()) {
                        svc.shutdown();
                    }
                    svc.shutdownNow();
                }
                private boolean isCompleted() {
                    return completed == tasks.length;
                }
                private boolean isExpired() {
                    return (System.currentTimeMillis() - startTimeMillis) > timeAvailableMillis;
                }
            };
            interruptor.start();
            
            final long interval = timeAvailableMillis / tasks.length;
            
            long sleptMillis = 0;
            for(Runnable task:tasks) {
                svc.submit(task);
                this.sleep(interval);
                sleptMillis += interval;
            }
            
            long timeLeftMillis = timeAvailableMillis - sleptMillis;
            if(timeLeftMillis < 0) {
                timeLeftMillis = 0;
            }
            
            com.bc.util.Util.shutdownAndAwaitTermination(svc, timeLeftMillis, TimeUnit.MILLISECONDS);
            
        }catch(RuntimeException e) {
            XLogger.getInstance().log(Level.WARNING, "Unexpected exception.", this.getClass(), e);
        }
    }
    
    public void runSingleProcess() {
        
        try{
            
            Runnable [] tasks = this.getTasks();
            
            final long sleepTime = TimeUnit.MINUTES.toMillis(this.getTimeAvailableMinutes()) / tasks.length;
            
            for(int i=0; i<tasks.length; i++) {
                tasks[i].run();
                if(i < tasks.length-1) {
                    this.sleep(sleepTime);
                }
            }
        }catch(RuntimeException e) {
            XLogger.getInstance().log(Level.WARNING, "Unexpected exception.", this.getClass(), e);
        }
    }
    
    private void sleep(long sleepTime) {
        Thread curr = Thread.currentThread();
        try { Thread.sleep(sleepTime); }catch(InterruptedException e) { XLogger.getInstance().log(Level.WARNING, "Interrupted, thread: "+curr.getName()+"#id: "+curr.getId(), this.getClass(), e); curr.interrupt(); }
    }

    @Override
    public boolean archiveFeeds() {
XLogger.getInstance().log(Level.INFO, "Archiving feeds", this.getClass());
        boolean success = super.archiveFeeds(); 
XLogger.getInstance().log(Level.INFO, "Done archiving feeds", this.getClass());
        return success;
    }

    @Override
    public boolean downloadFeeds() {
XLogger.getInstance().log(Level.INFO, "Downloading feeds", this.getClass());
        boolean success = super.downloadFeeds(); 
XLogger.getInstance().log(Level.INFO, "Done downloading feeds", this.getClass());
        return success;
    }
    
    private static volatile int x = 0;
    public boolean updateUsersitehitcounts() {
        if(++x == 4) {
            x = 0;
            try{
XLogger.getInstance().log(Level.INFO, "Updating user site hit counts", this.getClass());
                new Updateusersitehitcounts().run();
XLogger.getInstance().log(Level.INFO, "Done updating user site hit counts", this.getClass());
                return true;
            }catch(RuntimeException e) {
                XLogger.getInstance().log(Level.WARNING, "Unexpected exception", this.getClass(), e);
            }
        }
        return false;
    }
    
    public boolean updateFeedCache() {
        try{
XLogger.getInstance().log(Level.INFO, "Updating feed cache", this.getClass());
            new FeedCache().updateCache();
XLogger.getInstance().log(Level.INFO, "Done updating feed cache", this.getClass());
            return true;
        }catch(RuntimeException e) {
            XLogger.getInstance().log(Level.WARNING, "Unexpected exception", this.getClass(), e);
            return false;
        }
    }
    
    public Runnable [] getTasks() {
        Runnable [] tasks = {
            new Runnable(){
                @Override
                public void run() {
                    IdiscUpdateTask.this.downloadFeeds(); 
                    ++completed;
                }
            },
            new Runnable(){
                @Override
                public void run() {
                    IdiscUpdateTask.this.archiveFeeds();
                    ++completed;
                }
            },
            new Runnable(){
                @Override
                public void run() {
                    IdiscUpdateTask.this.updateFeedCache();
                    ++completed;
                }
            }
//                ,
//            new Runnable(){
//                @Override
//                public void run() {
//                    IdiscUpdateTask.this.updateUsersitehitcounts();
//                    ++completed;
//                }
//            }
        };
        return tasks;
    }

    public boolean isMultipleProcesses() {
        return multipleProcesses;
    }

    public void setMultipleProcesses(boolean multipleProcesses) {
        this.multipleProcesses = multipleProcesses;
    }
}
