package com.idisc.web.exceptions;

import javax.servlet.ServletException;

/**
 * @author poshjosh
 */
public class InstallationException extends ServletException {

    /**
     * Creates a new instance of <code>InstallationException</code> without
     * detail message.
     */
    public InstallationException() {
    }

    /**
     * Constructs an instance of <code>InstallationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InstallationException(String msg) {
        super(msg);
    }
}
