
import com.bc.util.JsonFormat;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
 * @author Chinomso Bassey Ikwuagwu on Sep 23, 2017 7:01:49 PM
 */
public class TestJsonData {

    @Test
    public void test() throws IOException, ParseException {
        final String fname = "META-INF/json/appProperties.json";
        final Path path = Paths.get(System.getProperty("user.home"), "Documents", 
                "NetBeansProjects", "idisc", "src", "main", "resources", fname);
        System.out.println(path);
        final Map map = null;//new Appproperties().load(path.toString());
        
        final JSONObject json = (JSONObject)map;
        System.out.println("===================== Map =====================");
        System.out.println(map);
        System.out.println("===================== JSONObject =====================");
        System.out.println(json.toJSONString());
        System.out.println("===================== JsonFormat =====================");
        System.out.println(new JsonFormat(true).toJSONString(json));
        JSONParser parser = new JSONParser();
        final Object obj = parser.parse(json.toJSONString());
    }
}
