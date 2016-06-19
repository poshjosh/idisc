package com.idisc.web.servlets.handlers.request;

import com.idisc.web.LoginBase;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import org.junit.Test;

/**
 * @author Josh
 */
public class LoginTest extends LoginBase {
 
    @Test
    public void test() throws ServletException, IOException, InterruptedException, ExecutionException {
        this.login();
    }
}
