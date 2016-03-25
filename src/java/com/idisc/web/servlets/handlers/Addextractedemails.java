package com.idisc.web.servlets.handlers;

import com.bc.util.XLogger;
import com.idisc.core.IdiscApp;
import com.idisc.pu.entities.Extractedemail;
import com.idisc.pu.entities.Installation;
import com.bc.jpa.ControllerFactory;
import com.bc.jpa.EntityController;
import com.idisc.pu.References;
import com.idisc.pu.entities.Emailstatus;
import java.util.Date;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import org.json.simple.JSONObject;


/**
 * @(#)Addextractedemails.java   26-Mar-2015 19:23:42
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class Addextractedemails extends AbstractUpdateValues {

    @Override
    public String [] getNames() {
        return new String[]{"extractedemails"};
    }

    @Override
    protected int execute(String name, Object values, Installation installation) throws Exception {
                
        JSONObject emailsAndUsernames = (JSONObject)values;
        
        ControllerFactory factory = IdiscApp.getInstance().getControllerFactory();

        EntityController<Extractedemail, Integer> ec = factory.getEntityController(Extractedemail.class, Integer.class);
        
        int created = 0;
        
        EntityManager em = ec.getEntityManager();
        
        try{
            
            em.getTransaction().begin();

XLogger.getInstance().log(Level.FINE, "Adding {0} contacts", this.getClass(), emailsAndUsernames.size());

            Emailstatus emailstatus = (Emailstatus)factory.getEnumReferences().getEntity(References.emailstatus.unverified);
            
            for(Object email:emailsAndUsernames.keySet()) {

                if(email == null) {
                    continue;
                }

                Object username = emailsAndUsernames.get(email);

                Extractedemail found = ec.selectFirst("emailAddress", email);

                if(found == null) {

                    found = new Extractedemail();
                    
                    found.setEmailstatus(emailstatus);
                    found.setDatecreated(new Date());
                    found.setEmailAddress(email.toString());
                    found.setInstallationid(installation);
                    found.setUsername(username==null?null:username.toString());

                    em.persist(found);

                    ++created;
                }
            }

            em.getTransaction().commit();

XLogger.getInstance().log(Level.FINE, "Added {0} contacts", this.getClass(), created);
            
        }finally{
            
            if(em != null) {
                em.close();
            }
        }
        
        return created;
    }
}

