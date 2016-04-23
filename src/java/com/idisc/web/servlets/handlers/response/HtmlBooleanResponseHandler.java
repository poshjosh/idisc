package com.idisc.web.servlets.handlers.response;

import com.bc.util.Util;
import com.bc.util.XLogger;
import com.idisc.web.exceptions.ValidationException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import javax.servlet.ServletContext;
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
            String referer = request.getHeader("referer");
            if(referer != null) {
                ServletContext context = request.getServletContext();
                try{
                    URL url = context.getResource(context.getContextPath());
XLogger.getInstance().log(Level.FINER, "Context URL from ServletContext.getResource(contextPath)", this.getClass(), url);
                    if(url == null) {
                        String urlStr = (String)context.getAttribute("baseURL")+context.getContextPath();
XLogger.getInstance().log(Level.FINER, "Context URL from ServletContext.getAttribute(baseURL)+contextPath", this.getClass(), urlStr);
                        url = new URL(urlStr);
                    }
                    targetPage = Util.getRelativePath(referer, url.toExternalForm());
                }catch(MalformedURLException e) {
                    targetPage = super.getTargetPage(request, name, success);
                }
            }else{
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
