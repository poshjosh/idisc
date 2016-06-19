package temp;

import com.idisc.web.WebApp;
import com.idisc.web.servlets.request.AsyncRequestTask;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.AsyncContext;

/**
 * @author Josh
 */
public class AsyncRequestTaskWorker extends Thread {
    
    private final AtomicBoolean shutdownRequested = new AtomicBoolean(false);
    
    private final ExecutorService executorService;

    private final Queue<AsyncContext> requestQueue;

    public AsyncRequestTaskWorker(String name, int poolSize) {
        this.setDaemon(true);
        this.setName(name);
        this.setPriority(java.lang.Thread.MAX_PRIORITY-1);
        this.requestQueue = new ConcurrentLinkedQueue<>();
        this.executorService = WebApp.getInstance().createFixedThreadPoolExecutorService(poolSize);
    }

    @Override
    public void run() {
        
        while(!shutdownRequested.get()) {
            
            if(!requestQueue.isEmpty()) {
              
                final AsyncContext asyncContext = requestQueue.poll();

//                executorService.execute(new AsyncRequestTask(asyncContext));             
            }
        }
    }
    
    public void add(AsyncContext asyncContext) {
        
        synchronized(requestQueue) {
        
            requestQueue.add(asyncContext);
        }
    }
    
    public void requestShutdown() {
        this.shutdownRequested.set(true);
    }
    
    public void shutdown(long timeout, TimeUnit timeUnit) {
        
        this.requestShutdown();
    
        if(this.executorService != null) {

          com.bc.web.core.util.ServletUtil.shutdownAndAwaitTermination(
                  this.executorService, timeout, timeUnit);  
        }
    }
}
