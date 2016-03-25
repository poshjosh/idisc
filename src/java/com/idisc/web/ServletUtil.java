package com.idisc.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @(#)Util.java   17-Oct-2014 20:17:42
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
public class ServletUtil extends com.bc.webapputil.ServletUtil {
    
    public static String getMessage(Throwable t) {
        if(t.getCause() != null) {
            t = t.getCause();
        }
        String msg = t.getMessage();
        return msg == null ? t.toString() : msg;
    }
    
    public static InputStream getInputStream(String path) throws IOException {
        try{
            URL url = new URL(path);
            return url.openStream();
        }catch(MalformedURLException e) {
            return new FileInputStream(path);
        }
    }
    
    /**
     * Mirrors logic of method {@link java.io.File#getName()}.
     * Use this method if its not necessary to create a new File object.
     * @param path The path to the file whose name is required
     * @return The name of the file at the specified path
     */
    public static String getFileName(String path) {
        String output = getFileName(path, File.separatorChar);
        if(output == null) {
            output = getFileName(path, '/');
            if(output == null) {
                output = getFileName(path, '\\');
            }
        }
        return output;
    }
    
    private static String getFileName(String path, char separatorChar) {
	int index = path.lastIndexOf(separatorChar);
	if (index == -1 || index == 0) return null;
	return path.substring(index + 1);
    }
}
