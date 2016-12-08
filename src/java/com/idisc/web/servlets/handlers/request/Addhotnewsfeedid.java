/*
 * Copyright 2016 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.idisc.web.servlets.handlers.request;

import com.bc.util.XLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Chinomso Bassey Ikwuagwu on Nov 20, 2016 1:37:04 AM
 */
public class Addhotnewsfeedid extends AbstractRequestHandler<Boolean> {
    
  private static final int limit = 20;
  
  private static final List<Integer> hotnewsFeedids = new ArrayList<>(limit);
    
  @Override
  protected Boolean execute(HttpServletRequest request) throws ServletException {

    final int feedid = (int)this.getLongParameter(request, "feedid", -1L);

XLogger.getInstance().log(Level.FINE, "#execute(HttpServletRequest) Feed ID: {0}", this.getClass(), feedid);     
    
    if(feedid == -1) {
        
        return Boolean.FALSE;
    }
    
    hotnewsFeedids.add(feedid);
    
XLogger.getInstance().log(Level.FINE, "hot news feedids: {0}", this.getClass(), hotnewsFeedids);     
    
    if(hotnewsFeedids.size() >= limit - 1) {
        
        hotnewsFeedids.subList(0, limit/2).clear();
        
XLogger.getInstance().log(Level.FINE, "After truncation, hot news feedids: {0}", this.getClass(), hotnewsFeedids);     
    }
    
    return Boolean.TRUE;
  }
  
  public static List<Integer> getHotnewsFeedids() {
    return hotnewsFeedids.isEmpty() ? Collections.EMPTY_LIST : Collections.unmodifiableList(hotnewsFeedids);
  }
}
