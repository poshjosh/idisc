/*
 * Copyright 2017 NUROX Ltd.
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

package com.idisc.web;

import com.idisc.core.IdiscAppImpl;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.configuration.ConfigurationException;

/**
 * @author Chinomso Bassey Ikwuagwu on Jan 16, 2017 5:34:38 PM
 */
public class IdiscAppDevmode extends IdiscAppImpl {

    public IdiscAppDevmode() 
            throws ConfigurationException, IOException, IllegalAccessException, 
            InterruptedException, InvocationTargetException {
        super(
                "C:/Users/Josh/Documents/NetBeansProjects/idisc/web/META-INF/properties/idisccore.properties",
                "C:/Users/Josh/Documents/NetBeansProjects/idisc/web/META-INF/properties/idisc_scrapper_devmode.properties",
                "C:/Users/Josh/Documents/NetBeansProjects/idiscpu/test/META-INF/persistence.xml",
                false);
    }
}
