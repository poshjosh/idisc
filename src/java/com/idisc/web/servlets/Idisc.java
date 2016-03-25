package com.idisc.web.servlets;

/**
 * @(#)Idisc.java   16-Oct-2014 10:01:45
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */
import com.bc.util.XLogger;
import com.idisc.web.servlets.handlers.Getmultipleresults;
import com.idisc.web.servlets.handlers.RequestHandler;
import com.idisc.web.servlets.handlers.RequestHandlerProvider;
import com.idisc.web.servlets.handlers.RequestHandlerProviderImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class Idisc extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

XLogger.getInstance().log(Level.FINER, "Request URI: {0}", this.getClass(), request.getRequestURI());
        
        try{
            
            RequestHandlerProvider provider = this.getRequestHandlerProvider();
            
            String [] paramNames = provider.getRequestHandlerParamNames(request);
            
            if(paramNames == null || paramNames.length == 0) {
                throw new FileNotFoundException(request.getRequestURI());
            }
            
            RequestHandler handler;
            
            if(paramNames.length == 1) {
                
                handler = provider.getRequestHandler(paramNames[0]);

XLogger.getInstance().log(Level.FINER, "Request Handler: {0}", this.getClass(), handler);

                if(handler == null) {
                    throw new FileNotFoundException(request.getRequestURI());
                }
                
            }else {

                handler = new Getmultipleresults();
            }
            
            handler.processRequest(request, response); 
            
        }catch(IOException | ServletException | RuntimeException e) {
            throw e;
        }
    }
    
    private RequestHandlerProvider _rhp_accessViaGetter;
    public RequestHandlerProvider getRequestHandlerProvider() {
        if(_rhp_accessViaGetter == null) {
            _rhp_accessViaGetter = new RequestHandlerProviderImpl();
        }
        return _rhp_accessViaGetter;
            
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
