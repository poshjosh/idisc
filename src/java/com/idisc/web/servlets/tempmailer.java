package com.idisc.web.servlets;

/**
 * @(#)tempmailer.java   31-Mar-2015 14:28:59
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

import com.bc.util.XLogger;
import com.idisc.web.SendNewsAsEmailTask;
import com.idisc.web.ServletUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class tempmailer extends HttpServlet {
    
    private static Future<?> previousFuture;
    private static SendNewsAsEmailTask previousTask;
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(
            HttpServletRequest request, HttpServletResponse response) {
        
        StringBuilder output = new StringBuilder();
            
        PrintWriter writer = null;
        
        boolean htmlOpened = false;
   
        try {
            
            response.setContentType("text/html;charset=UTF-8");
            
            writer = response.getWriter();
            
            output.append("<html>");
            output.append("<head>");
            output.append("<title>Servlet tempmailer</title>");  
            output.append("</head>");
            output.append("<body>");
            output.append("<p style=\"font-size:1.5em\">");
            
            htmlOpened = true;
            
            // Append details befor calling sent
            this.appendDetails(output);

            String action = request.getParameter("action");

            if("send".equals(action)) {
                boolean sent = this.sendMail(request);
                output.append("<br/><br/>Sending emails: ").append(sent);
            }else if("cancel".equals(action)) {
                boolean cancelled = this.cancelSendMail(request);
                output.append("<br/><br/>Cancelled sending emails: ").append(cancelled);
            }else{
                output.append("<br/><br/>Action: ").append(action);
            }
        }catch(Exception e) {

            if(writer != null) {

                if(!htmlOpened) {
                    output.setLength(0);
                }
                
                String trace = ServletUtil.getStackTrace(e);
                
                writer.println(trace);
            }
        } finally { 
            
            if(htmlOpened) {
                output.append("</p>");
                output.append("</body>");
                output.append("</html>");
            }else{
                output.setLength(0);
            }
            
            if(writer != null) {
                
                writer.println(output.toString());
                writer.close();
            }
        }
    } 
    
    private void appendDetails(StringBuilder builder) {
        builder.append("Previous task: ").append(previousTask);
        if(previousFuture != null) {
            builder.append("<br/>Is cancelled: ").append(previousFuture.isCancelled());
            builder.append("<br/>Is done: ").append(previousFuture.isDone());
        }
        if(previousTask != null) {
            builder.append("<br/>Offset: ").append(previousTask.getOffset());
            Collection currentBatch = previousTask.getBatch();
            builder.append("<br/>Last batch has ").append(currentBatch==null?null:currentBatch.size()).append(" emails");
            builder.append("<br/>Send interval: ").append(previousTask.getSendInterval());
        }
    }
    
    private boolean sendMail(HttpServletRequest request) {
        
        int feedCount = this.getIntParameter(request, "feedcount");
        int offset = this.getIntParameter(request, "offset");
        int batchSize = this.getIntParameter(request, "batchsize");
        int sendInterval = this.getIntParameter(request, "sendinterval");
        String senderemail = request.getParameter("senderemail");
        String senderpassword = request.getParameter("senderpassword");
        
        return this.sendMail(feedCount <= 0 ? 3 : feedCount, offset, batchSize, sendInterval, senderemail, senderpassword);
    }
    
    private int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? - 1 : Integer.parseInt(value);
    }
    
    private boolean sendMail(
            final int feedCount, final int offset, 
            final int batchSize, final int sendInterval, 
            final String senderemail, final String senderpassword) {
        
XLogger.getInstance().log(Level.FINER, "#sendMail", this.getClass());

        if(previousFuture != null) {
            
XLogger.getInstance().log(Level.FINER, "#sendMail, previous is cancelled: {0}, previous is done: {1}", 
this.getClass(), previousFuture.isCancelled(), previousFuture.isDone());

            if(!previousFuture.isCancelled() && !previousFuture.isDone()) {
                
                return false;
                
            }else{
                
                previousFuture = null;
            }
        }else{
XLogger.getInstance().log(Level.FINER, "#sendMail, previous: {0}", this.getClass(), previousFuture);
        }
        
        previousTask = new SendNewsAsEmailTask(feedCount){
            @Override
            public char[] getSenderPassword() {
                return senderpassword == null ? super.getSenderPassword() : senderpassword.toCharArray();
            }
            @Override
            public String getSenderEmail() {
                return senderemail == null ? super.getSenderEmail() : senderemail;
            }
            @Override
            public int getSendInterval() {
                return sendInterval <= 0 ? super.getSendInterval() : sendInterval;
            }
            @Override
            public int getBatchSize() {
                return batchSize <=0 ? super.getBatchSize(): batchSize;
            }
            @Override
            public int getOffset() {
                return offset <= 0 ? super.getOffset() : offset;
            }
        };
        
        ExecutorService svc = Executors.newSingleThreadExecutor();
        
XLogger.getInstance().log(Level.FINE, "#sendMail, submitting task", this.getClass());

        previousFuture = svc.submit(previousTask);

        // Haba, waits 3 hours = server timeout
        //
//        ProcessManager.shutdownAndAwaitTermination(svc, 3, TimeUnit.HOURS);
        
        svc.shutdown();
        
        return true;
    }

    private boolean cancelSendMail(HttpServletRequest request) {
        if(previousFuture != null) {
            previousFuture.cancel(true);
            return true;
        }else {
            return false;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
