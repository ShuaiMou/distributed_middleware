package com.tomacat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serverlet {
    void init();
    void service(InputStream is, OutputStream os) throws IOException;
    void destroy();
}
