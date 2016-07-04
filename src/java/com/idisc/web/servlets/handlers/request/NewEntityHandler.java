package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.web.servlets.handlers.response.CreationResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.JpaContext;

/**
 * @author poshjosh
 */
public abstract class NewEntityHandler<E> extends AbstractRequestHandler<Boolean> {
    
  @Override
  public boolean isProtected() {
    return false;
  }
  
  public abstract Class<E> getEntityType();

  @Override
  protected ResponseContext<Boolean> createSuccessResponseContext(HttpServletRequest request) {
    return new CreationResponseContext(request);  
  }
  
  public EntityController<E, Object> getEntityController() {
    return this.getEntityController(this.getEntityType());
  }

  public <T> EntityController<T, Object> getEntityController(Class<T> entityType) {
    JpaContext factory = IdiscApp.getInstance().getJpaContext();
    return factory.getEntityController(entityType);
  }
}

