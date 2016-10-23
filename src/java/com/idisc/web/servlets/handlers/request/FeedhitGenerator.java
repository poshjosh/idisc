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

import com.bc.jpa.JpaContext;
import com.bc.task.AbstractStoppableTask;
import com.bc.util.XLogger;
import com.idisc.pu.entities.Feedhit;
import com.idisc.pu.entities.Installation;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author Chinomso Bassey Ikwuagwu on Oct 12, 2016 11:57:25 AM
 */
public class FeedhitGenerator extends AbstractStoppableTask<Integer> {

    private final JpaContext jpaContext;
    private final Installation installation;
    private final com.idisc.pu.entities.Feed feed;
    private final int count;

    public FeedhitGenerator(JpaContext jpaContext, Installation installation, 
            com.idisc.pu.entities.Feed feed, int count) {
        this.jpaContext = Objects.requireNonNull(jpaContext);
        this.installation = Objects.requireNonNull(installation);
        this.feed = Objects.requireNonNull(feed);
        if(count < 1) {
            throw new IllegalArgumentException();
        }
        this.count = count;
    }
    
    @Override
    protected Integer doCall() throws Exception {

        int generated = 0;

        try{

          while(generated < count) {

              final int batchSize = count - generated > 100 ? 100 : count - generated;

              final int batch = this.generateFeedhitsBatch(jpaContext, installation, feed, batchSize);

              generated += batch;

              if(batch < 1) {

                  break;
              }
          }
        }catch(Exception e) {
            XLogger.getInstance().log(Level.WARNING, "Error generating " + count + " hitcounts for: " + feed, this.getClass(), e);
        }

        return generated;
    }

    @Override
    public String getTaskName() {
        return this.getClass().getSimpleName();
    }
    
    private int generateFeedhitsBatch(
            JpaContext jpaContext, Installation installation, 
            com.idisc.pu.entities.Feed feed, int batchSize) {

        if(batchSize < 1) {
            return batchSize;
        }

        EntityManager em = jpaContext.getEntityManager(Feedhit.class);
        try{

          EntityTransaction t = em.getTransaction();
          try{

              t.begin();

              Date hittime = new Date();


              for(int i=0; i<batchSize; i++) {

                    Feedhit feedhit = new Feedhit();  
                    feedhit.setFeedid(feed);
                    feedhit.setHittime(hittime);
                    feedhit.setInstallationid(installation);

                    em.persist(feedhit);
                }

                t.commit();

            }finally{
                if(t.isActive()) {
                    t.rollback();
                }
            }
            return batchSize;
        }finally{
            em.close();
        }
    }
}
