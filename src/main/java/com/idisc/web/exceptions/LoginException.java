package com.idisc.web.exceptions;

import javax.servlet.ServletException;

/**
 * @author poshjosh
 */
public class LoginException extends ServletException {

    /**
     * Creates a new instance of <code>LoginException</code> without detail
     * message.
     */
    public LoginException() {
    }

    /**
     * Constructs an instance of <code>LoginException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LoginException(String msg) {
        super(msg);
    }
}
