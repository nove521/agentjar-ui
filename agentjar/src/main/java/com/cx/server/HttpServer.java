package com.cx.server;

import com.cx.server.web.handler.holder.GenerateHandle;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class HttpServer {

    public static boolean started = false;
    private static Server server = null;

    public static void start(int port) {

        if (started) {
            return;
        }

        started = true;

        server = new Server(port);

        ContextHandlerCollection contextHandlerCollection = GenerateHandle.generate();
        server.setHandler(contextHandlerCollection);
        server.setStopTimeout(5000);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stop() {
        new Thread(() -> {
            try {
                server.stop();
                started = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void main(String[] args) {
        start(9988);
    }

}
