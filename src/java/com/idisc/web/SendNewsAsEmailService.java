package com.idisc.web;

import com.idisc.core.Service;

/**
 * @(#)SendNewsAsEmailService.java   28-Mar-2015 15:31:04
 *
 * Copyright 2011 NUROX Ltd, Inc. All rights reserved.
 * NUROX Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license 
 * terms found at http://www.looseboxes.com/legal/licenses/software.html
 */

/**
 * @author   chinomso bassey ikwuagwu
 * @version  2.0
 * @since    2.0
 */
public class SendNewsAsEmailService extends Service {

    @Override
    public Runnable newTask() {
        return new SendNewsAsEmailTask(3);
    }
}
