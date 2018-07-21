
import com.bc.util.JsonFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.simple.JSONValue;
import org.junit.Test;

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

/**
 * @author Chinomso Bassey Ikwuagwu on Sep 16, 2017 12:38:47 AM
 */
public class TestJsonParameter {

    @Test
    public void test() {
        
        final List<Integer> input = Arrays.asList(6, 5, 4, 3, 2, 1, 0);
        System.out.println(" Input: " + input);
//        Collections.shuffle(list);
        final List<Integer> output = input.stream().limit(3).filter((num) -> num < 5).collect(Collectors.toList());
        System.out.println("Output: " + output);
        
        final JsonFormat fmt = new JsonFormat(false);
        final Set<String> numbers = new LinkedHashSet(Arrays.asList("1", "2", "3"));
        final Map<String, String> params = new HashMap<>();
        params.put("key", fmt.toJSONString(numbers));
        
        final String param = params.get("key");
        final Object json = JSONValue.parse(param);
System.out.println("Type: " + json.getClass());
System.out.println(json);
    }
}
