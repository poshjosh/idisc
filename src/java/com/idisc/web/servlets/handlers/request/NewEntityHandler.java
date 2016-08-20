package com.idisc.web.servlets.handlers.request;

import com.idisc.web.servlets.handlers.response.CreationResponseContext;
import com.idisc.web.servlets.handlers.response.ResponseContext;
import javax.servlet.http.HttpServletRequest;

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
}

