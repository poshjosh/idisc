package com.idisc.web.servlets.request;

import com.bc.util.XLogger;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

/**
 * @author Josh
 */
public class RequestRejectionExceptionHandler implements RejectedExecutionHandler {

    /**
     * Does nothing, which has the effect of discarding task r.
     *
     * @param runnable the runnable task requested to be executed
     * @param executor the executor attempting to execute this task
     */
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
XLogger.getInstance().log(Level.FINER, "Execution rejected by Executor: {0}", this.getClass(), executor);
    }
}
