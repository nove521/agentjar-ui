package com.cx.client;

import com.cx.utils.SOAServer;
import org.yx.main.SumkServer;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) throws IOException {
//        SOAServer.startZKServer();
        SumkServer.start();
    }
}
