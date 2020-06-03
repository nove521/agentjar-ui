package com.cx.utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class FileUtils {

    public static final String CLASS_SUFFIX = ".class";
    public static final String JAR = "jar";

    /**
     * 获取目录下所有类全名
     *
     * @param uri
     * @return
     */
    public static List<String> getDirAllClassPath(URI uri, String packName) {
        if (uri.getScheme().equals(JAR)) {
            return getDirAllClassPathByJar(uri);
        }

        return getDirAllClassPathByCommon(uri, packName);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = new URI("jar:file:/D:/project/xuexi/TestHotUpdate/agentjar/target/agentjar-1.0.jar!/com/cx/server");

        List<String> dirAllClassPathByJar = getDirAllClassPathByJar(uri);


        System.out.println(uri.toString().replaceAll("\\.jar!.*$", ""));

        System.out.println(uri.getFragment());
        System.out.println(uri.getScheme());
        System.out.println(uri.getHost());
        System.out.println(uri.getSchemeSpecificPart());
        File file = new File(uri.getSchemeSpecificPart());
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
        }
    }

    /**
     * 获取普通目录下所有类全名
     *
     * @param uri
     * @return
     */
    public static List<String> getDirAllClassPathByJar(URI uri) {
        String uriPath = uri.toString().replace("jar:file:", "");
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        String[] split = uri.getSchemeSpecificPart().split("!/");
        String jarPath = split[0];
        String packName = split[1];


        File file = new File(jarPath);

        if (!file.exists()) {
            throw new IllegalArgumentException("jar包不存在！");
        }

        try {
            JarFile jarFile = new JarFile(file);
            return jarFile.stream().filter(item -> {
                boolean contains = item.getName().contains(packName);
                boolean contains2 = item.getName().endsWith("/");
                return contains && !contains2;
            }).map(item -> item.getName().replace("/", ".").replace(CLASS_SUFFIX, "")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 获取普通目录下所有类全名
     *
     * @param uri
     * @return
     */
    public static List<String> getDirAllClassPathByCommon(URI uri, String packName) {
        File file = new File(uri);
        if (!file.exists()) {
            throw new IllegalArgumentException("路径不存在！");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("不是有效路径！");
        }
        List<String> list = new ArrayList<>();
        getFiles(file, list, new StringBuilder(packName));
        return list;
    }

    /**
     * 获取jar包下所有资源文件
     * jar:file:/D:/project/simple/target/simple-0.0.1.jar!/BOOT-INF/classes!/
     *
     * @param uri
     * @return
     */
    public static List<String> getDirAllResourceByJar(URI uri) {
        String uriPath = uri.toString().replace("jar:file:", "");
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        String[] split = uri.getSchemeSpecificPart().split("!/");
        String jarPath = split[0];
        String absolutePath = split[1];

        File file = new File(jarPath);

        if (!file.exists()) {
            throw new IllegalArgumentException("jar包不存在！");
        }

        try {
            JarFile jarFile = new JarFile(file);
            return jarFile.stream().filter(item -> item.getName().startsWith(absolutePath)).map(ZipEntry::getName).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static void getFiles(File file, List<String> list, StringBuilder packName) {

        if (file.getName().endsWith(CLASS_SUFFIX)) {
            packName.append(".").append(file.getName().replace(CLASS_SUFFIX, ""));
            list.add(packName.toString());
            return;
        }

        if (!file.isDirectory()) {
            return;
        }

        File[] files = file.listFiles();

        if (Objects.isNull(files)) {
            return;
        }

        int len = packName.length();
        for (File f : files) {
            if (f.isDirectory())
                packName.append(".").append(f.getName());
            getFiles(f, list, packName);
            packName.delete(len, packName.length());
        }
    }

    public static void getFiles(File file, List<String> list) {

        if (file.isFile()) {
            list.add(file.getPath().replace("\\", "/"));
            return;
        }

        if (!file.isDirectory()) {
            return;
        }

        File[] files = file.listFiles();

        if (Objects.isNull(files)) {
            return;
        }

        for (File f : files) {
            getFiles(f, list);
        }
    }

    public static String getJarFileCode(URI uri, String fileName) {
        String uriPath = uri.toString().replace("jar:file:", "");
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        String[] split = uri.getSchemeSpecificPart().split("!/");
        String jarPath = split[0];

        File file = new File(jarPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("jar包不存在！");
        }
        JarFile jarFile = null;
        InputStream inputStream = null;
        try {
            jarFile = new JarFile(file);
            ZipEntry entry = jarFile.getJarEntry(fileName);
            inputStream = jarFile.getInputStream(entry);
            return IoUtils.inputStreamToString(inputStream);
        } catch (IOException ignored) {
        }
        return null;
    }

    public static String getFileCode(URI uri, String fileName) {
        File file = getFile(uri, fileName);
        if (Objects.nonNull(file)) {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                return IoUtils.inputStreamToString(inputStream);
            } catch (IOException e) {
                return IoUtils.throwableErrToStr(e);
            }
        }
        return null;
    }

    public static File getFile(URI uri, String fileName) {
        File file = new File(uri);
        if (!file.exists()) {
            return null;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (Objects.isNull(files)) {
                return null;
            }
            for (File f : files) {
                File result = getFile(f.toURI(), fileName);
                if (Objects.nonNull(result)) {
                    return result;
                }
            }
        }

        String filePath = file.getPath().replace("\\","/");
        if (filePath.endsWith(fileName)) {
            return file;
        }
        return null;
    }

    public static int saveJarFileCode(URI uri, String fileName, String code) {
        String uriPath = uri.toString().replace("jar:file:", "");
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
            return -1;
        }
        String[] split = uri.getSchemeSpecificPart().split("!/");
        String jarPath = split[0];
        return JarTool.writeJar(jarPath, fileName, code);
    }

    public static int saveFileCode(URI uri, String fileName, String code) {
        File file = getFile(uri, fileName);
        if (Objects.nonNull(file)) {
            FileOutputStream outputStream = null;
            InputStream inputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                inputStream = new ByteArrayInputStream(code.getBytes());
                IoUtils.inputStreamToOutputStream(inputStream, outputStream, true);
                return 1;
            } catch (IOException ignored) {
            }
        }
        return -1;
    }

    public static int saveFile(String path, String copyPath) {
        byte[] byteByPath = IoUtils.getByteByPath(path);
        if (byteByPath.length == 0) {
            return -1;
        }
        IoUtils.toFileByByte(byteByPath, copyPath);
        return 1;
    }
}
