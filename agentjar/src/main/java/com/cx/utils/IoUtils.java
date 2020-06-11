package com.cx.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class IoUtils {

    public static String inputStreamToString(File file) throws IOException {
        return inputStreamToString(new FileInputStream(file), true);
    }

    public static String inputStreamToString(InputStream inputStream, boolean close) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while ((n = inputStream.read(buf, 0, buf.length)) >= 0) {
            outputStream.write(buf, 0, n);
        }
        String result = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        outputStream.close();
        if (close) {
            inputStream.close();
        }
        result = result.replace("\u0000", "");
        return result;
    }

    public static byte[] inputStreamToByte(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            int n;
            while ((n = inputStream.read(buf, 0, buf.length)) >= 0) {
                outputStream.write(buf, 0, n);
            }
            return outputStream.toByteArray();
        } catch (IOException ignored) {
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException ignored) {
            }
        }
        return new byte[0];
    }


    public static void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream, boolean close) throws IOException {
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) >= 0) {
            outputStream.write(buf, 0, len);
        }
        if (close) {
            outputStream.close();
            inputStream.close();
        }
    }

    public static void toFileByByte(byte[] buff, String path) {

        String dirPath = path.substring(0, path.lastIndexOf("\\"));
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

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
            inputStreamToOutputStream(inputStream, outputStream, true);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String throwableErrToStr(Throwable e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(out);
        e.printStackTrace(outputStream);
        outputStream.close();
        return out.toString();
    }

    public static void closeSteam(Closeable closeable) {
        if (Objects.nonNull(closeable)) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
