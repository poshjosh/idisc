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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import org.json.simple.JSONArray;
import org.junit.Test;

/**
 * @author Josh
 */
public class AddbookmarkfeedidsTest extends LoginBase {
    
    public AddbookmarkfeedidsTest() {  }
    
    @Test
    public void test() throws ServletException, IOException {
        
        Map<String, String> parameters = new HashMap<>();
        parameters.putAll(this.getDefaultOutputParameters());
        
        String paramName = new Addbookmarkfeedids().getRequestParameterName();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(Arrays.asList(524171));
        parameters.put(paramName, jsonArray.toJSONString());
        
        this.execute("/index.jsp", parameters, "/"+Addbookmarkfeedids.class.getSimpleName().toLowerCase());
    }
}
