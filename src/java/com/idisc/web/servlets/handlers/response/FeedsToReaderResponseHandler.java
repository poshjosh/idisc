package com.idisc.web.servlets.handlers.response;

import com.idisc.pu.entities.Feed;
import com.idisc.pu.entities.Installation;
import java.io.Reader;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Josh
 */
public class FeedsToReaderResponseHandler<E extends Feed> extends ListToReaderResponseHandler<E> {

  private final Installation installation;
  
  public FeedsToReaderResponseHandler() { 
    this.installation = null;
  }

  public FeedsToReaderResponseHandler(Installation installation) { 
    this.installation = installation;
  }

  public FeedsToReaderResponseHandler(Installation installation, int bufferSize) {
    super(bufferSize);
    this.installation = installation;
  }
    
  @Override
  public Reader getOutput(HttpServletRequest request, String name, List<E> feeds) {
      
    List compositeOutput = new FeedsJsonResponseHandler(installation).buildCompositeOutput(request, name, feeds);
    
    return this.getJsonOutputReader(request, name, compositeOutput);
  }
}
