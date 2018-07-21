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

package com.idisc.web;

import java.math.BigDecimal;

/**
 * @author Chinomso Bassey Ikwuagwu on Nov 21, 2016 3:39:46 PM
 */
public interface MemoryManager {

    long getAvailableMemoryAtStartup();

    /**
     * @return currently available memory divided by the memory available at start up
     */
    BigDecimal getMemoryLevel();
    
    long getAvailableMemory();
    
    double limit(double size, double minimum);
    
    int limit(int size, int minimum);

}
