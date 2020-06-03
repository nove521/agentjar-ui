package com.cx.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class JarTool {

    public static final String BAK_DIR_PAHT = "bak";


    public static int writeJar(String jarPath, String fileName, String code) {
        String[] pa = jarPath.split(String.valueOf(jarPath.charAt(0)));
        int len = pa.length;
        String[] newPa = Arrays.copyOf(pa, len + 1);
        newPa[len] = newPa[len - 1];
        newPa[len - 1] = BAK_DIR_PAHT;
        String newJarPath = String.join("\\", newPa);
        int res = FileUtils.saveFile(jarPath, newJarPath);
        if (res < 0) {
            return res;
        }

        File file = new File(jarPath);
        if (!file.exists()) {
            return -1;
        }
        File bakFile = new File(newJarPath);
        try {
            return writeJar(file, bakFile, fileName, code);
        } catch (IOException e) {
            file.delete();
            FileUtils.saveFile(newJarPath, jarPath);
            return -1;
        }
    }

    protected static int writeJar(File targetJar, File bakJar, String fileName, String code) throws IOException {
        JarFile bakJarFile = new JarFile(bakJar);
        List<JarEntry> bakJarEntries = new ArrayList<>();
        bakJarFile.stream().forEach(bakJarEntries::add);
        return writeJar(targetJar, bakJarFile, bakJarEntries, fileName, code);
    }


    protected static int writeJar(File targetJar, JarFile jarFile, List<JarEntry> jarEntries, String fileName, String path) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(targetJar);
        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream);
        try {
            for (JarEntry jarEntry : jarEntries) {
                if (jarEntry.getName().endsWith(fileName)) {
                    jarOutputStream.putNextEntry(new JarEntry(fileName));
                    jarOutputStream.write(path.getBytes());
                } else {
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    jarOutputStream.putNextEntry(jarEntry);
                    IoUtils.inputStreamToOutputStream(inputStream, jarOutputStream, false);
                    inputStream.close();
                }
            }
        } finally {
            jarOutputStream.close();
            jarFile.close();
        }
        return 1;
    }


    public static void main(String[] args) {
//        int asdasd = writeJar("D:\\project\\simple\\target\\simple-0.0.1.jar", "BOOT-INF/classes/application.properties", "asdasd");
//        System.out.println(asdasd);
        String x = "\\D:\\project\\simple\\target\\simple-0.0.1.jar";
        String[] pa = x.split("\\\\");
        int len = pa.length;
        String[] newPa = Arrays.copyOf(pa, len + 1);
        newPa[len] = newPa[len - 1];
        newPa[len - 1] = BAK_DIR_PAHT;
        String newJarPath = String.join("\\", newPa);
        System.out.println(newJarPath);

    }

}
