package com.tomacat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TestServer {

    private static String WEB_ROOT = System.getProperty("user.dir") + "/" + "WebContent";
    private static String URL = "";
    private static HashMap<String, String> map;

    static {
        map = new HashMap<String, String>(10);
        String WEB_ROOT = System.getProperty("user.dir") + "/WebContent/config/servelet.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(WEB_ROOT)));
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry entry : entries){
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            serverSocket = new ServerSocket(8080);
            while (true){
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                parse(inputStream);
                if (null != URL){
                    if (URL.indexOf(".") != -1){
                        sendStaticResource(outputStream);
                    }else {
                        sendDynamicResource(inputStream, outputStream);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {

                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != socket) {
                    socket.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private static void sendDynamicResource(InputStream inputStream, OutputStream outputStream) {
        try {
            outputStream.write("HTTP/1.1 200 ok\n".getBytes());
            outputStream.write("Server:apache-Coyote/1.1\n".getBytes());
            outputStream.write("Content-Type:text/html;charset=utf-8\n".getBytes());
            outputStream.write("\n".getBytes());

            if (map.containsKey(URL)){
                String value = map.get(URL);

                Class clazz = Class.forName(value);
                Serverlet instance = (Serverlet) clazz.newInstance();
                instance.init();
                instance.service(inputStream, outputStream);
                instance.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    //发送静态资源
    private static void sendStaticResource(OutputStream outputStream) {
        //存放本次请求的静态资源的内容
        byte[] bytes = new byte[2048];
        FileInputStream fileInputStream = null;
        try {
            File file = new File(WEB_ROOT, URL);
            if (file.exists()){
                //向客户端输出 http 协议的响应行，响应头
                outputStream.write("HTTP/1.1 200 ok\n".getBytes());
                outputStream.write("Server:apache-Coyote/1.1\n".getBytes());
                outputStream.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                outputStream.write("\n".getBytes());

                fileInputStream = new FileInputStream(file);

                int ch = fileInputStream.read(bytes);
                while (ch != -1){
                    outputStream.write(bytes, 0, ch);
                    ch = fileInputStream.read(bytes);
                }
            }else {
                outputStream.write("HTTP/1.1 404 not found\n".getBytes());
                outputStream.write("HTTP/1.1 200 ok\n".getBytes());
                outputStream.write("Server:apache-Coyote/1.1\n".getBytes());
                outputStream.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                outputStream.write("\n".getBytes());

                String errorMessage = "file not found";
                outputStream.write(errorMessage.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //获取HTTP协议的请求部分，截取客户要访问的资源名称，将这个资源名称复制给url。
    private static void parse(InputStream inputStream) throws IOException {
        StringBuffer content = new StringBuffer(2048);
        byte[] buffer = new byte[2048];

        int i = -1;
        i = inputStream.read(buffer);

        for (int j = 0; j < i; j++){
            content.append((char)buffer[j]);
        }
        System.out.println(content);
        parseUrl(content);

    }

    private static void parseUrl(StringBuffer content) {
        int index1, index2;
        index1 = content.indexOf(" ");
        if (index1 != -1) {
            index2 = content.indexOf(" ", index1 + 1);
            if (index2 > index1){
                URL = content.substring(index1 + 2, index2);

            }
        }
    }
}
