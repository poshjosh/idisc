package com.idisc.web.servlets.handlers.request;

import java.util.logging.Logger;
import com.idisc.pu.References;
import com.idisc.pu.entities.Emailstatus;
import com.idisc.pu.entities.Extractedemail;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.logging.Level;
import org.json.simple.JSONObject;
import com.bc.jpa.context.JpaContext;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class Addextractedemails extends AbstractUpdateValues {
    private transient static final Logger LOG = Logger.getLogger(Addextractedemails.class.getName());
    
  @Override
  public String[] getNames() {
    return new String[] { "extractedemails" };
  }
  
  @Override
  protected int execute(HttpServletRequest request, String name, Object values, Installation installation)
    throws Exception {
      
    JSONObject emailsAndUsernames = (JSONObject)values;
    
    JpaContext jpaContext = getJpaContext(request);
    
    int created = 0;
    
    try (com.bc.jpa.dao.Select<Extractedemail> select = jpaContext.getDaoForSelect(Extractedemail.class)){
        
      if(LOG.isLoggable(Level.FINE)){
         LOG.log(Level.FINE, "Adding {0} contacts", emailsAndUsernames.size());
      }
      
      final Emailstatus emailstatus = (Emailstatus)jpaContext.getEnumReferences().getEntity(References.emailstatus.unverified);
      
      select.begin();
      
      for (Object email : emailsAndUsernames.keySet()) {
          
        if (email != null) {

          Object username = emailsAndUsernames.get(email);
          
          select.reset(); // Without reset reusing a dao will throw an exception
          
          final List<Extractedemail> foundList = select
                  .where(Extractedemail.class, "emailAddress", email)
                  .createQuery().setMaxResults(1).getResultList();
          
          Extractedemail found = foundList == null || foundList.isEmpty() ? null : foundList.get(0);
          
          if (found == null) {
              
            found = new Extractedemail();
            
            found.setEmailstatus(emailstatus);
            found.setDatecreated(new Date());
            found.setEmailAddress(email.toString());
            found.setInstallationid(installation);
            found.setUsername(username == null ? null : username.toString());
            
            select.persist(found);
            
            created++;
          }
        }
      }
      
      select.commit();
      
      if(LOG.isLoggable(Level.FINE)){
         LOG.log(Level.FINE, "Added {0} contacts", created);
      }
      
    }
    
    return created;
  }
}
