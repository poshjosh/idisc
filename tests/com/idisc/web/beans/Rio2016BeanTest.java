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
package com.idisc.web.beans;

import com.idisc.web.LoginBase;
import javax.servlet.http.HttpServletRequest;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author Josh
 */
public class Rio2016BeanTest extends LoginBase {
    
    public Rio2016BeanTest() {  }
    
    /**
     * Test of getMedalsTableHtml(boolean) method, of class Rio2016Bean.
     */
    @Test
    public void testGetMedalsTableHtml() {
        
        log("getMedalsTableHtml(boolean)");
        
        Rio2016Bean instance = new Rio2016Bean();

//        this.setLogLevel(Level.FINE, instance.getClass().getName());
        
        HttpServletRequest request = this.createRequest();
        
        instance.setRequest(request);
        
        String tableHtml = instance.getMedalsTableHtml(true);
        
        assertFalse("Failed to remove all tags with class='table-expand'", tableHtml.contains("table-expand"));
        
log(tableHtml);

//        tableHtml = instance.getMedalsTableHtml(false);
//log(tableHtml);

        
//        fail("The test case is a prototype.");
    }
    
}
