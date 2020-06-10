package com.cx.utils;

import com.cx.enums.StatusSys;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class JarTool {

    public static final String JAR_PREFIX = "jar:";
    public static final String ALL_JAR_PREFIX = "jar:file:";
    public static final String IS_JAR_REX = "^.*\\.jar!/.*$";
    public static final String JAR_SUFFIX = ".jar";

    public static StatusSys writeJar(String jarPath, String fileName, String code) {
        File file = new File(jarPath);
        if (!file.exists()) {
            return StatusSys.NOT_EXISTS_FAIL;
        }
        StatusSys bakRes = FileUtils.bakFile(file);
        if (bakRes.isFail()) {
            return StatusSys.NO_MAIN_FAIL;
        }
        String newJarPath = FileUtils.newPathToPail(jarPath, FileUtils.BAK_DIR_PAHT);
        File bakFile = new File(newJarPath);
        try {
            return writeJar(file, bakFile, fileName, code);
        } catch (IOException e) {
            file.delete();
            FileUtils.saveFile(newJarPath, jarPath);
            return StatusSys.FAIL;
        }
    }

    protected static StatusSys writeJar(File targetJar, File bakJar, String fileName, String code) throws IOException {
        JarFile bakJarFile = new JarFile(bakJar);
        List<JarEntry> bakJarEntries = new ArrayList<>();
        bakJarFile.stream().forEach(bakJarEntries::add);
        return writeJar(targetJar, bakJarFile, bakJarEntries, fileName, code);
    }


    protected static StatusSys writeJar(File targetJar, JarFile jarFile, List<JarEntry> jarEntries, String fileName, String path) {

        FileOutputStream fileOutputStream;
        JarOutputStream jarOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetJar);
            jarOutputStream = new JarOutputStream(fileOutputStream);

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

            return StatusSys.SUCCEED;
        } catch (IOException e) {
            return StatusSys.FAIL;
        } finally {
            IoUtils.closeSteam(jarOutputStream);
            IoUtils.closeSteam(jarFile);
        }
    }


    public static void main(String[] args) {
//        int asdasd = writeJar("D:\\project\\simple\\target\\simple-0.0.1.jar", "BOOT-INF/classes/application.properties", "asdasd");
//        System.out.println(asdasd);
        String x = "\\D:\\project\\simple\\target\\simple-0.0.1.jar";
        String[] pa = x.split("\\\\");
        int len = pa.length;
        String[] newPa = Arrays.copyOf(pa, len + 1);
        newPa[len] = newPa[len - 1];
        newPa[len - 1] = FileUtils.BAK_DIR_PAHT;
        String newJarPath = String.join("\\", newPa);
        System.out.println(newJarPath);

    }

}
