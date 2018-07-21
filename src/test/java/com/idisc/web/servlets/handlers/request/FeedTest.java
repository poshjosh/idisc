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

import com.idisc.web.LoginBase;
import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Test;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 14, 2016 12:49:02 AM
 */
public class FeedTest extends LoginBase {

    @Test
    public void test() throws ServletException, IOException {
        
        this.test(524244);
    }
    
    public void test(int feedid) throws ServletException, IOException {
        
        this.execute("/index.jsp", "feedid="+feedid+"&installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&limit=20&format=application/json&tidy=true", "/feed");
    }
}
