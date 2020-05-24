package com.cx.agent;

import org.yx.main.SumkServer;
import org.yx.util.S;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

public class LoadedAgent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        try {
            Session.setIns(inst);
            Collection<String> options = S.json.fromJson(agentArgs,(Type)Collection.class);
            SumkServer.start(Collections.unmodifiableCollection(options));
        }catch (Error e){
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
