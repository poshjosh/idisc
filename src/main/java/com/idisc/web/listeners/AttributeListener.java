/*
 * Copyright 2018 NUROX Ltd.
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
package com.idisc.web.listeners;

import java.util.logging.Logger;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Web application lifecycle listener.
 *
 * @author Josh
 */
public class AttributeListener implements ServletContextAttributeListener, HttpSessionAttributeListener, ServletRequestAttributeListener {

    private transient static final Logger LOG = Logger.getLogger(AttributeListener.class.getName());

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) { 
        LOG.fine(() -> "Added to ServletContext: " + event.getName());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) { 
        LOG.fine(() -> "Removed from ServletContext: " + event.getName());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) { 
        LOG.fine(() -> "Replaced for ServletContext: " + event.getName());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) { 
        LOG.finer(() -> "Added to Session: " + event.getName());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) { 
        LOG.finer(() -> "Removed from Session: " + event.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) { 
        LOG.finer(() -> "Replaced for Session: " + event.getName());
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) { 
        LOG.finest(() -> "Added to Request: " + event.getName());
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) { 
        LOG.finest(() -> "Removed from Request: " + event.getName());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) { 
        LOG.finest(() -> "Replaced for Request: " + event.getName());
    }
}
