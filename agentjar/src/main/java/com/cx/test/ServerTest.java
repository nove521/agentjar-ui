package com.cx.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

public class ServerTest {

    public static void main(String[] args) {
        Server server = new Server(9988);
        try {
            ContextHandler contextHandler = new ContextHandler();
            contextHandler.setContextPath("/test");
            contextHandler.setHandler(new HelloWorldHandle());
            server.setHandler(contextHandler);

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
