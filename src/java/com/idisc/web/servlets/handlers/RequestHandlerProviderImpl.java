package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class RequestHandlerProviderImpl implements RequestHandlerProvider {
    
    @Override
    public String getRequestHandlerParamName(HttpServletRequest request) {
        String token = request.getParameter(com.idisc.web.RequestParameters.REQ);
        if(token == null) {
            String servletPath = request.getServletPath();
            if(servletPath != null) {
                int n = servletPath.indexOf('/');
                if(n != -1) {
                    token = servletPath.substring(n+1);
                }        
            }        
        }
        return token;
    }

    @Override
    public String [] getRequestHandlerParamNames(HttpServletRequest request) {
        String [] tokens = request.getParameterValues(com.idisc.web.RequestParameters.REQ);
        if(tokens == null || tokens.length == 0) {
            String servletPath = request.getServletPath();
            if(servletPath != null) {
                int n = servletPath.indexOf('/');
                String token;
                if(n != -1) {
                    token = servletPath.substring(n+1);
                    if(!token.isEmpty()) {
                        tokens = token.split(",");
                    }
                }        
            }        
        }
        return tokens;
    }
    
    /**
     * @param request
     * @return 
     */
    @Override
    public RequestHandler getRequestHandler(HttpServletRequest request) {
        
        final String key = this.getRequestHandlerParamName(request);

        RequestHandler handler = this.getRequestHandler(key);
        
        return handler;
    }

    @Override
    public RequestHandler getRequestHandler(String name) {
        String className = this.toClassName(com.idisc.web.servlets.handlers.Feeds.class.getPackage().getName(), name);
        try{
            return getRequestHandler((Class<RequestHandler>)Class.forName(className));
        }catch(ClassNotFoundException e) {
            XLogger.getInstance().log(Level.WARNING, "Failed to create new instance of: "+className, this.getClass(), e);
            return null;
        }
    }

    @Override
    public RequestHandler getRequestHandler(Class<RequestHandler> aClass) {
        try{
            return aClass.getConstructor().newInstance();
        }catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            XLogger.getInstance().log(Level.WARNING, "Failed to create new instance of: "+aClass.getName(), this.getClass(), e);
            return null;
        }
    }
    
    /**
     * <p>For inputs <tt>prefix</tt> and <tt>abc</tt> returns <tt>prefix.Abc</tt></p>
     * <p>For inputs <tt>prefix</tt> and <tt>/ABC</tt> returns <tt>prefix.Abc</tt></p>
     * @param prefix
     * @param paramValue
     * @return The input parameters formatted into an appropriate class name
     */
    private String toClassName(String prefix, String paramValue) {
        StringBuilder builder = new StringBuilder(prefix.length() + 1 + paramValue.length());
        builder.append(prefix).append('.');
        int indexOfFirst = 0;
        for(int i=0; i<paramValue.length(); i++) {
            char ch = paramValue.charAt(i);
            if(i == 0) {
                if(ch == '/') {
                    indexOfFirst = 1;
                    continue;
                }
            }
            if(i == indexOfFirst) {
                ch = Character.toTitleCase(ch);
            }else{
                ch = Character.toLowerCase(ch);
            }
            builder.append(ch);
        }
XLogger.getInstance().log(Level.FINER, "Prefix: {0}, param: {1}, class name: {2}",
        this.getClass(), prefix, paramValue, builder);
        return builder.toString();
    }
}
