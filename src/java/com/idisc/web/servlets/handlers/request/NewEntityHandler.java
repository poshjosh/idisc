package com.idisc.web.servlets.handlers.request;

import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.core.IdiscApp;
import com.idisc.web.servlets.handlers.response.EntityJsonBooleanResponseHandler;
import com.idisc.web.servlets.handlers.response.HtmlBooleanResponseHandler;
import com.idisc.web.servlets.handlers.response.ResponseHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author poshjosh
 */
public abstract class NewEntityHandler<E> extends AbstractRequestHandler<Boolean>
{
  @Override
  public boolean isProtected()
  {
    return false;
  }
  
  public abstract Class<E> getEntityType();

  @Override
  public ResponseHandler<Boolean> createResponseHandler(HttpServletRequest request) {
    ResponseHandler<Boolean> responseHandler;
    if (this.isHtmlResponse(request))
    {
      responseHandler = new HtmlBooleanResponseHandler(){
        @Override
        public int getStatusCode(HttpServletRequest request, String name, Boolean success) {
          return NewEntityHandler.this.getStatusCode(success);
        }
      };
    }
    else
    {
      responseHandler = new EntityJsonBooleanResponseHandler(){
        @Override
        public int getStatusCode(HttpServletRequest request, String name, Boolean success) {
          return NewEntityHandler.this.getStatusCode(success);
        }
      };
    }
    return responseHandler;
  }
  
  private int getStatusCode(Boolean success)
  {
    return success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  }
  
  public EntityController<E, Object> getEntityController()
  {
    return this.getEntityController(this.getEntityType());
  }

  public <T> EntityController<T, Object> getEntityController(Class<T> entityType) {
    ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();
    return factory.getEntityController(entityType);
  }
}

