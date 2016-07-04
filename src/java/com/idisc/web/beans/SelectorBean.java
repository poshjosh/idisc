package com.idisc.web.beans;

import com.bc.jpa.EntityController;
import com.bc.jpa.query.JPQL;
import com.idisc.core.IdiscApp;
import com.idisc.web.servlets.request.RequestParameters;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.JpaContext;

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
    
    private JpaContext cf;
    
    private JPQL<E> jpql;
    
    public void setRequest(HttpServletRequest request) {
        
        cf = IdiscApp.getInstance().getJpaContext();
        
        if(this.isAddParametersFromRequest()) {
            
            Map map = new RequestParameters(request);
            
            final int size = map.isEmpty() ? 1 : map.size() + 1;
            
            params = new HashMap(size, 1.0f);
            
            if(!map.isEmpty()) {
                params.putAll(map);
            }
            if(columnName != null && columnValue != null) {
                params.put(columnName, columnValue);
            }
        }else{
            if(columnName != null && columnValue != null) {
                params = Collections.singletonMap(columnName, columnValue);
            }
        }
        this.jpql = cf.getJpql(this.getEntityClass());
    }
    
    public E getSingleResult() {
        return jpql.getSingleResult(params, "AND", null, true);
    }
    
    public List<E> getResultList() {
        return jpql.getResultList(params, "AND", null, offset, limit, true);
    }
    
    public List<Map<String, ?>> getResultListMappings() {
        EntityController<E, ?> ec = cf.getEntityController(this.getEntityClass());
        return ec.toMapList(this.getResultList(), -1);
    }
    
    public String getTableName() {
        return cf.getMetaData().getTableName(entityClass);
    }

    public void setTableName(String table) {
        this.entityClass = cf.getMetaData().findEntityClass(table);
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
