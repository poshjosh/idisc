package com.idisc.web.servlets.handlers.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author USER
 */
public class BooleanToJsonResponseHandler<E extends Boolean> extends ObjectToJsonResponseHandler<E> {
    
    @Override
    public int getStatusCode(HttpServletRequest request, String name, Boolean success) {
        int statusCode;
        if(success) {
            statusCode = HttpServletResponse.SC_OK;
        }else{
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return statusCode;
    }
}
