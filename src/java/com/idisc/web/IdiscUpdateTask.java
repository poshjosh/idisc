package com.idisc.web;

import com.bc.util.XLogger;
import com.idisc.core.FeedUpdateTask;
import com.idisc.core.Updateusersitehitcounts;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class IdiscUpdateTask
  extends FeedUpdateTask
{
  private boolean multipleProcesses;
  private volatile int completed;
  
  @Override
  public void run()
  {
    if (isMultipleProcesses()) {
      runMultipleProcesses();
    } else {
      runSingleProcess();
    }
  }
  
  public int getInterTaskSpacingMinutes() {
    return 2;
  }
  
  public int getFeedCycleIntervalMinutes() {
    return WebApp.getInstance().getConfiguration().getInt("feedCycleInterval", 15);
  }
  
  public int getTimeAvailableMinutes() {
    return getFeedCycleIntervalMinutes() - getInterTaskSpacingMinutes();
  }
  
  public void runMultipleProcesses()
  {
    try
    {
        final Runnable[] tasks = getTasks();
      
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
    }
    catch (RuntimeException e) {
      XLogger.getInstance().log(Level.WARNING, "Unexpected exception.", getClass(), e);
    }
  }

  public void runSingleProcess()
  {
    try
    {
      Runnable[] tasks = getTasks();
      
      long sleepTime = TimeUnit.MINUTES.toMillis(getTimeAvailableMinutes()) / tasks.length;
      
      for (int i = 0; i < tasks.length; i++) {
        tasks[i].run();
        if (i < tasks.length - 1) {
          sleep(sleepTime);
        }
      }
    } catch (RuntimeException e) {
      XLogger.getInstance().log(Level.WARNING, "Unexpected exception.", getClass(), e);
    }
  }
  
  private void sleep(long sleepTime) {
    Thread curr = Thread.currentThread();
    try { Thread.sleep(sleepTime); } catch (InterruptedException e) { XLogger.getInstance().log(Level.WARNING, "Interrupted, thread: " + curr.getName() + "#id: " + curr.getId(), getClass(), e);curr.interrupt();
    }
  }
  
  public int archiveFeeds() {
    XLogger.getInstance().log(Level.INFO, "Archiving feeds", getClass());
    int updateCount = super.archiveFeeds();
    XLogger.getInstance().log(Level.INFO, "Done archiving feeds", getClass());
    return updateCount;
  }
  
  public boolean downloadFeeds()
  {
    XLogger.getInstance().log(Level.INFO, "Downloading feeds", getClass());
    boolean success = super.downloadFeeds();
    XLogger.getInstance().log(Level.INFO, "Done downloading feeds", getClass());
    return success;
  }
  
  private static volatile int x = 0;
  
  public boolean updateUsersitehitcounts() { if (++x == 4) {
      x = 0;
      try {
        XLogger.getInstance().log(Level.INFO, "Updating user site hit counts", getClass());
        new Updateusersitehitcounts().run();
        XLogger.getInstance().log(Level.INFO, "Done updating user site hit counts", getClass());
        return true;
      } catch (RuntimeException e) {
        XLogger.getInstance().log(Level.WARNING, "Unexpected exception", getClass(), e);
      }
    }
    return false;
  }
  
  public boolean updateFeedCache() {
    try {
      XLogger.getInstance().log(Level.INFO, "Updating feed cache", getClass());
      new FeedCache().updateCache();
      XLogger.getInstance().log(Level.INFO, "Done updating feed cache", getClass());
      return true;
    } catch (RuntimeException e) {
      XLogger.getInstance().log(Level.WARNING, "Unexpected exception", getClass(), e); }
    return false;
  }

  private Runnable[] _tasks;
  public Runnable[] getTasks()
  {
    if(_tasks == null) {
      _tasks = this.createTasks();
    }
    return _tasks;
  }
  
  private Runnable [] createTasks() {

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
    return this.multipleProcesses;
  }
  
  public void setMultipleProcesses(boolean multipleProcesses) {
    this.multipleProcesses = multipleProcesses;
  }
}
