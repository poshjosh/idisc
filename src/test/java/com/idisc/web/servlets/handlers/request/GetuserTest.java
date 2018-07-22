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
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Test;

/**
 *
 * @author Josh
 */
public class GetuserTest extends LoginBase {

    @Test
    public void test() throws ServletException, IOException, ParseException {
        
        final String name = "getuser";
        
        Object output = this.execute("/index.jsp", "installationid=2&installationkey=abdb33ee-a09e-4d7d-b861-311ee7061325&format=application/json&tidy=true", "/"+name);
        
        JSONObject json = (JSONObject)JSONValue.parseWithException(output.toString());
        
        Object target = json.get(name);
        
        Assert.assertNotNull(target);
        
log("Target: "+target);
    }
}