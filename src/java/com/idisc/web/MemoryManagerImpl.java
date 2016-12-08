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

import com.bc.util.XLogger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;

/**
 * @author Chinomso Bassey Ikwuagwu on Nov 21, 2016 3:41:02 PM
 */
public class MemoryManagerImpl implements MemoryManager {

  private final long memoryAtStartup;

  public MemoryManagerImpl() {
    this.memoryAtStartup = this.getAvailableMemory();
XLogger.getInstance().log(Level.INFO, "Memory at startup: {0}, available processors: {1}", 
        this.getClass(), this.memoryAtStartup, Runtime.getRuntime().availableProcessors());
  }

    @Override
  public int limit(int size, int minimum) {
    return (int)this.doLimit(size, minimum);
  }
  
  @Override
  public double limit(double size, double minimum) {
    return this.doLimit(size, minimum);
  }
  
  private double doLimit(double size, double minimum) {
    final float memoryLevel = this.getMemoryLevel().floatValue();
    double result;
    if(memoryLevel >= 1.0f) {
      result = size;
    }else{
      result = size * memoryLevel;
    }
XLogger.getInstance().log(Level.FINER, 
        "Based on memory level: {0}, formatted input from {1} to {2} ", 
        this.getClass(), memoryLevel, size, result);
    return result;
  }

  /**
   * @return currently available memory divided by the memory available at start up
   */
  @Override
  public BigDecimal getMemoryLevel() {
    BigDecimal freeMemoryObj = new BigDecimal(this.getAvailableMemory());
    BigDecimal memoryAtStartupObj = new BigDecimal(this.memoryAtStartup);
    return freeMemoryObj.divide(memoryAtStartupObj, 2, RoundingMode.HALF_UP);
  }

  @Override
  public long getMemoryAtStartup() {
    return this.memoryAtStartup;
  }

  @Override
  public long getAvailableMemory() {
    Runtime runtime = Runtime.getRuntime();
    long totalMemory = runtime.totalMemory(); // current heap allocated to the VM process
    long freeMemory = runtime.freeMemory(); // out of the current heap, how much is free
    long maxMemory = runtime.maxMemory(); // Max heap VM can use e.g. Xmx setting
    long usedMemory = totalMemory - freeMemory; // how much of the current heap the VM is using
    long availableMemory = maxMemory - usedMemory; // available memory i.e. Maximum heap size minus the current amount used
    return availableMemory;
  }  
}
