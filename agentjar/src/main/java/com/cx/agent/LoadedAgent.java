package com.cx.agent;

import com.cx.utils.StrUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Type;

public class LoadedAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        try {
            Session.setIns(inst);
            JsonArray options = StrUtils.json().fromJson(agentArgs, (Type) JsonArray.class);
            ContextManage.start(options);
        } catch (Error e) {
            e.printStackTrace();
        }

    }

    private static byte[] readFileToByte(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        byte[] b = new byte[fileInputStream.available()];
        fileInputStream.read(b);
        fileInputStream.close();
        return b;
    }
}
