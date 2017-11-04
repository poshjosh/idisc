package com.idisc.web.beans;

import com.bc.jpa.controller.EntityController;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.context.JpaContext;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;

/**
 * @param <E>
 * @author Josh
 */
public class SelectorBean<E> implements Serializable {

    private boolean addParametersFromRequest;
    
    private int offset = -1;
    
    private int limit = -1;
    
    private Class<E> entityClass;
    
    private String columnName;
    
    private Object columnValue;
    
    private Map params;
    
    private JpaContext jpaContext;
    
//    private JPQL<E> jpql;
    
    public void setRequest(HttpServletRequest request) {
        
        AppContext appCtx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        jpaContext = appCtx.getIdiscApp().getJpaContext();

        params = new HashMap();
            
        if(this.isAddParametersFromRequest()) {
            
            Map map = new RequestParameters(request);
       
            if(!map.isEmpty()) {
                params.putAll(map);
            }
            if(columnName != null) {
                params.put(columnName, columnValue);
            }
        }else{
            if(columnName != null) {
                params = Collections.singletonMap(columnName, columnValue);
            }
        }
    }
    
    public E getSingleResult() {
        return jpaContext.getDaoForSelect(entityClass).where(entityClass, params).getSingleResultAndClose();
    }
    
    public List<E> getResultList() {
        return jpaContext.getDaoForSelect(entityClass).where(entityClass, params).getResultsAndClose(offset, limit);
    }
    
    public List<Map<String, ?>> getResultListMappings() {
        EntityController<E, ?> ec = jpaContext.getEntityController(this.getEntityClass());
        return ec.toMapList(this.getResultList(), -1);
    }
    
    public String getTableName() {
        return jpaContext.getMetaData().getTableName(entityClass);
    }

    public void setTableName(String table) {
        this.entityClass = jpaContext.getMetaData().findEntityClass(table);
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<E> entityClass) {
        this.entityClass = entityClass;
    }
    
    public boolean isAddParametersFromRequest() {
        return addParametersFromRequest;
    }

    public void setAddParametersFromRequest(boolean addParametersFromRequest) {
        this.addParametersFromRequest = addParametersFromRequest;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(Object columnValue) {
        this.columnValue = columnValue;
    }
}
