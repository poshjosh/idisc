package com.idisc.web.servlets.handlers;

/**
 * @author Josh
 */
public class Appproperties extends DownloadJson {

    @Override
    public String getFilename() {
        return "META-INF/json/appProperties.json";
    }
}
