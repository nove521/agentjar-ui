package com.cx.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IoUtils {

    public static String inputStreamToString(File file) throws IOException {
        return inputStreamToString(new FileInputStream(file));
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (inputStream.read(buf) >= 0) {
            outputStream.write(buf);
        }
        String result = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        outputStream.close();
        inputStream.close();

        result = result.replace("\r","");
        result = result.replace("\u0000","");

        return result;
    }
}
