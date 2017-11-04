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

import com.bc.jpa.context.JpaContext;
import com.idisc.pu.entities.Installation;
import com.idisc.web.AppContext;
import com.idisc.web.Attributes;
import com.idisc.web.LoginBase;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import org.json.simple.JSONArray;

/**
 * @author Josh
 */
public abstract class AbstractPreferenceSyncTest extends LoginBase {
    
    public AbstractPreferenceSyncTest() {  }
    
    public abstract AbstractPreferenceSync getSyncHandler();
    
    public Integer getDefaultInstallationid() {
        return 2;
    }
    
    public JSONArray getTestFeedids() {
        return this.getJsonArray(new Integer[]{525659});
    }

    public JSONArray getTestFeedidsToAdd() {
        return this.getJsonArray(new Integer[]{525846, 525857});
    } 

    public JSONArray getTestFeedidsToRemove() {
        return this.getTestFeedidsToAdd();
    }
    
    public void testAddAndRemove() throws ServletException, IOException {
        
        JpaContext jpaContext = this.getJpaContext();
        
        JSONArray feedids = this.getTestFeedids();
        JSONArray feedidsToAdd = this.getTestFeedidsToAdd();
        JSONArray feedidsToRemove = this.getTestFeedidsToRemove();
        
        AbstractPreferenceSync syncHandler = this.getSyncHandler();
        
        final String key = syncHandler.getKey();
        final String key_addfeedids = "com.looseboxes.idisc.common."+key+".feedids_to_add";
        final String key_removefeedids = "com.looseboxes.idisc.common."+key+".feedids_to_remove";

        Map<String, String> params = new HashMap();
        params.putAll(this.getDefaultOutputParameters());
        
        params.put("com.looseboxes.idisc.common."+key+".feedids", feedids.toJSONString());
        params.put(key_addfeedids, feedidsToAdd.toJSONString());
        
        final String servletPath = "/"+key;
        
        Object addrequestOutput = this.execute("/index.jsp", params, servletPath);
this.log("Addrequest output:\n"+addrequestOutput);

        params.remove(key_addfeedids);
        params.put(key_removefeedids, feedidsToRemove.toJSONString());

        Object removerequestOutput = this.execute("/index.jsp", params, servletPath);
this.log("Removerequest output:\n"+removerequestOutput);

        final Integer installationid = this.getDefaultInstallationid();
        final Installation instl = this.getInstallation(jpaContext, installationid);

        List<com.idisc.pu.entities.Feed> added = syncHandler.add(jpaContext, feedids, instl);
        
this.log("Added: "+added);
        
        List<com.idisc.pu.entities.Feed> removed = syncHandler.add(jpaContext, feedids, instl);
        
this.log("Removed: "+removed);
    }
    
    public Installation getInstallation(JpaContext jpaContext) {
        return this.getInstallation(jpaContext, this.getDefaultInstallationid());
    }
    
    public Installation getInstallation(JpaContext jpaContext, Integer installationid) {
        
        EntityManager em = jpaContext.getEntityManager(Installation.class);
        
        return em.find(Installation.class, installationid);
    }
    
    public JpaContext getJpaContext() {
        
        AppContext appContext = (AppContext)this.getServletContext().getAttribute(Attributes.APP_CONTEXT);
        
        JpaContext jpaContext = appContext.getIdiscApp().getJpaContext();
        
        return jpaContext;
    }

    public JSONArray getJsonArray(Integer [] arr) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(Arrays.asList(arr));
        return jsonArray;
    } 
}
