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

        result = result.replace("\u0000", "");

        return result;
    }

    public static void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) >= 0) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();
    }

    public static void toFileByByte(byte[] buff, String path) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] getByteByPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return new byte[0];
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            inputStreamToOutputStream(inputStream, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
