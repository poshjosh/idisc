/*
 * Copyright 2018 NUROX Ltd.
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

import java.io.Serializable;

/**
 * @author Chinomso Bassey Ikwuagwu on Jul 9, 2018 4:49:15 PM
 */
public class FeedSelectionConfig implements Serializable {

    private int maxAgeDays = 3;
    
    private int maxSpread = 2_000;
    
    private int batchSize = 100;
    
    private int maxOutputSize = 20;

    public FeedSelectionConfig() { }

    public int getMaxAgeDays() {
        return maxAgeDays;
    }

    public void setMaxAgeDays(int maxAgeDays) {
        this.maxAgeDays = maxAgeDays;
    }

    public int getMaxSpread() {
        return maxSpread;
    }

    public void setMaxSpread(int maxSpread) {
        this.maxSpread = maxSpread;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getMaxOutputSize() {
        return maxOutputSize;
    }

    public void setMaxOutputSize(int maxOutputSize) {
        this.maxOutputSize = maxOutputSize;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.maxAgeDays;
        hash = 89 * hash + this.maxSpread;
        hash = 89 * hash + this.batchSize;
        hash = 89 * hash + this.maxOutputSize;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FeedSelectionConfig other = (FeedSelectionConfig) obj;
        if (this.maxAgeDays != other.maxAgeDays) {
            return false;
        }
        if (this.maxSpread != other.maxSpread) {
            return false;
        }
        if (this.batchSize != other.batchSize) {
            return false;
        }
        if (this.maxOutputSize != other.maxOutputSize) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FeedSelectionConfig{" + "maxAgeDays=" + maxAgeDays + ", maxSpread=" + maxSpread + ", batchSize=" + batchSize + ", maxOutputSize=" + maxOutputSize + '}';
    }
}
