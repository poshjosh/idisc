package com.idisc.web.servlets.handlers.response;

import com.idisc.web.exceptions.ValidationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author poshjosh
 */
public class HtmlBooleanResponseHandler extends HtmlResponseHandler<Boolean> {

    @Override
    public String getTargetPage(HttpServletRequest request, String name, Boolean success) throws ValidationException {
        String targetPage;
        if(success) {
            // Back to sender
            targetPage = this.getRefererRelativePath(request);
            if(targetPage == null) {
                targetPage = super.getTargetPage(request, name, success);                    
            }
        }else{
            targetPage = "/oops.jsp";
        }
        return targetPage;
    }

  @Override
    protected String getResponseMessage(HttpServletRequest request, String name, Boolean success)
    {
        String message;
        if(success) {
            message = name + " successful";
        }else{
            message = name + " failed";
        }
        return message;
    }

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
