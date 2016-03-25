package com.idisc.web.servlets.handlers;

import com.idisc.core.IdiscApp;
import com.idisc.web.AppProperties;
import com.idisc.web.RequestParameters;
import com.idisc.web.WebApp;
import com.idisc.web.exceptions.ValidationException;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.Configuration;


/**
 * @(#)Select.java   02-Dec-2014 02:13:51
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 * @param <T>
 */
public abstract class Select<T> extends BaseRequestHandler<List<Map>> {
    
    private final long defaultLimit;
    
    private final long maxLimit;
    
    public Select() {
        Configuration config = WebApp.getInstance().getConfiguration();
        defaultLimit = config == null ? 100 : config.getLong(AppProperties.DEFAULT_LIMIT, 100);
        maxLimit = config == null ? 200 : config.getLong(AppProperties.MAX_LIMIT, 200);
    }
    
    protected abstract Class<T> getEntityClass();

    private transient EntityController ec_accessViaGetter;
    public EntityController<T, Object> getEntityController() {
        if(ec_accessViaGetter == null) {
            ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
            ec_accessViaGetter = factory.getEntityController(this.getEntityClass());
        }
        return ec_accessViaGetter;
    }    
    
    @Override
    public List<Map> execute(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<T> entities = this.select(request);
        
        List<Map> output = this.toMap(entities);
        
        return output;
    }

    protected List<T> select(
            HttpServletRequest request) throws ValidationException {
        
        return this.getEntityController().select(
                this.getSearchParams(request), 
                this.getOrderBy(request), 
                this.getOffset(request), 
                this.getLimit(request));
    }
    
    protected Map<String, Object> getSearchParams(HttpServletRequest request) throws ValidationException {
        return null;
    }
    
    protected Map<String, String> getOrderBy(HttpServletRequest request) throws ValidationException {
        return null;
    }

    protected Date getAfter(HttpServletRequest request) throws ValidationException {
        String sval = request.getParameter(RequestParameters.AFTER);
        Date after;
        try{
            after = sval == null ? null : new Date(Long.parseLong(sval));
        }catch(NumberFormatException e) {
            throw new ValidationException("Invalid value for parameter '"+RequestParameters.AFTER+"': "+sval);
        }
        return after;
    }
    
    protected int getOffset(HttpServletRequest request) throws ValidationException {
        final int offset = getInt(request, RequestParameters.OFFSET, 0);
        return offset;
    }
    
    protected int getLimit(HttpServletRequest request) throws ValidationException {
        int limit = getInt(request, RequestParameters.LIMIT, (int)defaultLimit);
        if(limit > maxLimit) {
            limit = (int)maxLimit;
        }
        return limit;
    }
    
    protected List<Map> toMap(List<T> entities) {
        
        List<Map> output;
        if(entities != null && !entities.isEmpty()) {
            output = new ArrayList(entities.size());
            EntityController<T, Object> ec = this.getEntityController();
            for(T feed:entities) {
                
                Map map = toMap(ec, feed);
                
                if(map == null || map.isEmpty()) {
                    continue;
                }
                output.add(map);
            }
        }else{
            output = Collections.emptyList();
        }
        
        return output;
    }
    
    protected Map toMap(EntityController<T, Object> ec, T entity) {
        
        if(ec == null) {
            ControllerFactory cf = IdiscApp.getInstance().getControllerFactory();
            ec = cf.getEntityController(this.getEntityClass());
        }
        
        return ec.toMap(entity, false);
    }
    
    protected int getInt(HttpServletRequest request, String key, int defaultValue) {
        String val = request.getParameter(key);
        if(val == null || val.isEmpty()) {
            return defaultValue;
        }else{
            return Integer.parseInt(val);
        }
    }
}
