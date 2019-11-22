package com.tomacat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginServerlet implements Serverlet {
    public void init() {
        System.out.println("LoginServerlet ... destroy");
    }

    public void service(InputStream is, OutputStream os) throws IOException {
        System.out.println("LoginServerlet ... destroy");
        os.write("login to the system".getBytes());
        os.flush();
    }

    public void destroy() {
        System.out.println("LoginServerlet ... destroy");
    }
}
