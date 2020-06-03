package com.cx.agent;

import com.cx.server.HttpServer;
import com.cx.server.myBeanMnage.MyBeanFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * 上下文管理器
 */
public class ContextManage {

    private static MyBeanFactory factory = null;

    public static void start(JsonArray options) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("port", 19998);
        options.add(jsonObject);

        factory = new MyBeanFactory();
        factory.init();
        startSocket(options.get(0).getAsJsonObject());
    }

    private static void startSocket(JsonObject options) {
        JsonElement portElement = options.get("port");
        if (Objects.isNull(portElement)) {
            return;
        }
        HttpServer.start(portElement.getAsInt());
    }

    public static void stop() {
        HttpServer.stop();
        factory.clear();
        factory = null;
    }

    public static MyBeanFactory getFactory() {
        if (Objects.isNull(factory)) {
            throw new NullPointerException("bean工厂未初始化");
        }
        return factory;
    }

}
