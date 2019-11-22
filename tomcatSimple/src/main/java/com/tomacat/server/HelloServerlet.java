package com.tomacat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HelloServerlet implements Serverlet {
    public void init() {
        System.out.println("HelloServerlet ... init");
    }

    public void service(InputStream is, OutputStream os) throws IOException {
        System.out.println("HelloServerlet ... service");
        os.write("hello every one".getBytes());
        os.flush();
    }

    public void destroy() {
        System.out.println("HelloServerlet ... destroy");
    }
}
