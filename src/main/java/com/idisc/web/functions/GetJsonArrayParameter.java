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

package com.idisc.web.functions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import javax.servlet.ServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 * @author Chinomso Bassey Ikwuagwu on Sep 16, 2017 12:47:17 AM
 */
public class GetJsonArrayParameter implements BiFunction<ServletRequest, String, List<String>> {

  @Override
  public List<String> apply(ServletRequest request, String key) {
      
      final String str = request.getParameter(key);
      
      final List<String> output;
      
      if(str == null || str.isEmpty()) {
      
          output = Collections.EMPTY_LIST;
          
      }else{
          
          final JSONArray arr = (JSONArray)JSONValue.parse(str);
          
          output = arr == null ? Collections.EMPTY_LIST : arr;
      }
      
      return Objects.requireNonNull(output);
  }
}
