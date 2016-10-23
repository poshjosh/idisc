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

package com.idisc.web.servlets.request;

import com.bc.jpa.JpaContext;
import com.bc.util.XLogger;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Feedhit_;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.ConfigNames;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.bc.jpa.dao.BuilderForSelect;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 12, 2016 2:43:13 PM
 */
public class AppVersionCode {
    
  private final ServletContext context;
  
  private final Installation installation;
  
  public AppVersionCode(ServletContext context, Installation installation) {
    this.context = Objects.requireNonNull(context);
    this.installation = installation;
  }

  public boolean isLessThanLatest(HttpServletRequest request, boolean defaultValue) {
    final int latestVersion = this.getLatest(-1);
    boolean output = latestVersion == -1 ? defaultValue : this.isLessOrEquals(request, latestVersion-1, defaultValue);
    
XLogger.getInstance().log(Level.FINE, "Less than latest version: {0}, installation: {1}", 
        this.getClass(), output, installation);

    return output;    
  }
  
  public boolean isLessOrEquals(HttpServletRequest request, int target, boolean defaultValue) {
    boolean output;
    final int versionCode = this.get(request, -1);
    if(versionCode != -1) {
      output = versionCode <= target;
    }else{
      output = this.isLessOrEquals(target, defaultValue);
    }
    
XLogger.getInstance().log(Level.FINE, "Less than or equals version {0}: {1}, installtion: {2}", 
        this.getClass(), target, output, installation);

    return output;
  }
  
  public boolean isLessOrEquals(int versionCode, boolean defaultValue) {
    boolean output = defaultValue;
      if(installation != null) {
        Date lastInstallDate = installation.getLastinstallationdate();
        if(lastInstallDate != null) {
          Date nextVersionDate = this.getInstallationDate(versionCode + 1);
          if(nextVersionDate == null) {
            output = defaultValue;
          }else{
            if(lastInstallDate.after(nextVersionDate)) {
              output = false;
            }else{
              Date mostRecentFeedhit = this.getMostRecentFeedhit(installation, null);
              output = mostRecentFeedhit == null ? defaultValue : 
              mostRecentFeedhit.before(nextVersionDate) || mostRecentFeedhit.equals(nextVersionDate);
            }
          }
        }
      }  
    return output;
  }
  
  public Date getInstallationDate(int versionCode) {
    Calendar cal = Calendar.getInstance();
    switch(versionCode) {
      case 26:
        cal.set(2016, 8, 25); break; 
      case 25:
        cal.set(2016, 8, 24); break; 
      case 24:
        cal.set(2016, 8, 17); break; 
      case 23:
        cal.set(2016, 8, 16); break; 
      case 22:
        cal.set(2016, 7, 26); break; 
      case 21:
        cal.set(2016, 7, 16); break; 
      case 20:
        cal.set(2016, 7, 10); break;
      case 19:
      case 18:
        cal.set(2016, 7, 9); break;
      case 17:
        cal.set(2016, 7, 5); break;
      case 16:
        cal.set(2016, 4, 7); break;
      case 15:
        cal.set(2016, 3, 27); break;
      case 14:
      case 13:
        cal.set(2016, 3, 22);  break;
      case 12:
        cal.set(2016, 1, 17);  break;
      case 11:
      case 10:
      case 9:
        cal.set(2016, 0, 28); break;
      case 8:
      case 7:
        cal.set(2016, 0, 18);  break;
      case 6:
        cal.set(2016, 0, 6);  break;
      default:
        cal = null;
    }   
    return cal == null ? null : cal.getTime();
  }
  
  public Date getMostRecentFeedhit(Installation installation, Date defaultValue) {
    Objects.requireNonNull(installation);
    JpaContext jpaContext = this.getAppContext().getIdiscApp().getJpaContext();
    Date mostRecent;
    try(BuilderForSelect<Date> qb = jpaContext.getBuilderForSelect(Feedhit.class, Date.class)) {
      mostRecent = qb.select(Feedhit.class, Feedhit_.hittime.getName())
          .where(Feedhit_.installationid.getName(), installation)
          .descOrder(Feedhit_.hittime.getName())
          .createQuery().setFirstResult(0).setMaxResults(1).getSingleResult();
    }catch(NoResultException e) {
      mostRecent = defaultValue;   
    }
    return mostRecent;
  }
  
  public int getLatest(int defaultValue) {
    final int latestVersionCode = this.getAppContext().getConfiguration().getInt(
            ConfigNames.APP_VERSIONCODE_LATEST, defaultValue);
    return latestVersionCode;
  }

  public int get(HttpServletRequest request, int defaultValue) {
    int output;
    final String versionCode = request.getParameter("versionCode");  
    if(versionCode != null) {
      output = Integer.parseInt(versionCode);
    }else{
      output = defaultValue;  
    }
    return output;
  }  
  
  public AppContext getAppContext() {
    return (AppContext)context.getAttribute(Attributes.APP_CONTEXT);  
  }
}
