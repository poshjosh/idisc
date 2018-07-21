package com.idisc.web.beans;

import javax.servlet.http.HttpServletRequest;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;

/**
 * @param <E>
 * @author Josh
 */
public class SelectorBean<E> extends com.bc.web.core.beans.SelectorBean {

    @Override
    public void setRequest(HttpServletRequest request) {
        
        final AppContext appCtx = (AppContext)request.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        this.setPersistenceUnitContext(appCtx.getIdiscApp().getJpaContext());
        
        super.setRequest(request);
    }
}
