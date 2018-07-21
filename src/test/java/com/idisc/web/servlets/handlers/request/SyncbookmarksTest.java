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

import java.io.IOException;
import javax.servlet.ServletException;
import org.junit.Test;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 16, 2016 9:15:25 PM
 */
public class SyncbookmarksTest extends AbstractPreferenceSyncTest {
    
    @Override
    public AbstractPreferenceSync getSyncHandler() {
        
        return new Syncbookmarks();
    }

    @Test
    @Override
    public void testAddAndRemove() throws ServletException, IOException {
        super.testAddAndRemove();
    }
}
