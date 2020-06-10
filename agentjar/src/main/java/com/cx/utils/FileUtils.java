package com.cx.utils;

import com.cx.enums.StatusSys;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class FileUtils {

//    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static final String FILE_PREFIX = "file:";

    public static final String BAK_DIR_PAHT = "bakFile";

    /**
     * 获取目录下所有类全名
     *
     * @param uri
     * @return
     */
    public static List<String> getDirAllClassPath(URI uri, String packName) {
        if (uri.getScheme().equals("jar")) {
            return getDirAllClassPathByJar(uri);
        }

        return getDirAllClassPathByCommon(uri, packName);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        bakFile(new File("\\D:\\project\\xuexi\\TestHotUpdate\\agentjar\\target\\agentjar-1.0.jar"));
    }

    /**
     * 获取普通目录下所有类全名
     *
     * @param uri
     * @return
     */
    public static List<String> getDirAllClassPathByJar(URI uri) {
        String uriPath = uri.toString().replace(JarTool.ALL_JAR_PREFIX, "");
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
            }).map(item -> item.getName().replace("/", ".").replace(ClassUtils.CLASS_SUFFIX, "")).collect(Collectors.toList());
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
        String uriPath = uri.toString().replace(JarTool.ALL_JAR_PREFIX, "");
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

        if (file.getName().endsWith(ClassUtils.CLASS_SUFFIX)) {
            packName.append(".").append(file.getName().replace(ClassUtils.CLASS_SUFFIX, ""));
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

    public static String getJarFileCode(URI uri, String fileName) throws IOException {
        String uriPath = uri.toString().replace(JarTool.ALL_JAR_PREFIX, "");
        try {
            uri = new URI(uriPath);
        } catch (URISyntaxException e) {
//            logger.error(e.getMessage());
            return null;
        }

        String[] split = uri.getSchemeSpecificPart().split("!/");
        String jarPath = split[0];

        File file = new File(jarPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("jar包不存在！");
        }
        JarFile jarFile = new JarFile(file);
        ZipEntry entry = jarFile.getJarEntry(fileName);
        try {
            InputStream inputStream = jarFile.getInputStream(entry);
            return IoUtils.inputStreamToString(inputStream, true);
        } finally {
            jarFile.close();
        }
    }

    public static String getFileCode(URI uri, String fileName) {
        File file = getFile(uri, fileName);
        if (Objects.nonNull(file)) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                return IoUtils.inputStreamToString(inputStream, true);
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

        String filePath = file.getPath().replace("\\", "/");
        if (filePath.endsWith(fileName)) {
            return file;
        }
        return null;
    }

    public static StatusSys editJarFileCode(URI uri, String fileName, String code) throws URISyntaxException {
        String uriPath = uri.toString().replace(JarTool.ALL_JAR_PREFIX, "");
        uri = new URI(uriPath);
        String[] split = uri.getSchemeSpecificPart().split("!/");
        String jarPath = split[0];
        return JarTool.writeJar(jarPath, fileName, code);
    }

    public static StatusSys editFileCode(URI uri, String fileName, String code, boolean isBak) {
        File file = getFile(uri, fileName);

        if (Objects.nonNull(file)) {
            if (isBak) {
                StatusSys bakRes = FileUtils.bakFile(file);
                if (bakRes.isFail()) {
//                    logger.error("文件备份失败，不执行修改操作");
                    return StatusSys.NO_MAIN_FAIL;
                }
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = new ByteArrayInputStream(code.getBytes());
                IoUtils.inputStreamToOutputStream(inputStream, outputStream, true);
                return StatusSys.SUCCEED;
            } catch (IOException e) {
//                logger.error("保存普通文件内容的时候异常: {}", e.getMessage());
            }
        }
        return StatusSys.FAIL;
    }


    /**
     * 备份文件，在当前目录下新建一个目录存放备份文件
     * 如果已存在备份文件，则不备份，返回成功执行
     *
     * @param file 文件
     * @return 状态
     */
    public static StatusSys bakFile(File file) {
        try {
            String name = file.getName();
            String path = file.getAbsolutePath();
            String bakPath = FileUtils.newPathToPail(file.toURI(), FileUtils.BAK_DIR_PAHT);
            bakPath = FileUtils.replacePathFileName(bakPath, name);

            File backFile = new File(bakPath);
            if (backFile.exists()) {
                return StatusSys.OK_RUN;
            }

            return FileUtils.saveFile(path, bakPath);
        } catch (URISyntaxException e) {
            return StatusSys.FAIL;
        }
    }

    public static StatusSys saveFile(String path, String copyPath) {
        byte[] byteByPath = IoUtils.getByteByPath(path);
        if (byteByPath.length == 0) {
            return StatusSys.FAIL;
        }
        IoUtils.toFileByByte(byteByPath, copyPath);
        return StatusSys.SUCCEED;
    }

    /**
     * 专门给路径增加一个新层次
     *
     * @param url
     * @param insert
     * @return
     * @throws URISyntaxException
     */
    public static String newPathToPail(URI url, String insert) throws URISyntaxException {
        String filePath = url.getSchemeSpecificPart();
        String[] pa = filePath.split(String.valueOf(filePath.charAt(0)));
        return StrUtils.insertStrArr(pa, pa.length - 1, insert, "\\");
    }

    public static String newPathToPail(String filePath, String insert) {
        String[] pa = filePath.split(String.valueOf(filePath.charAt(0)));
        return StrUtils.insertStrArr(pa, pa.length - 1, insert, "\\");
    }

    public static String getPathFileName(String path, String split) {
        String[] pathArr = path.split(split);
        return pathArr[pathArr.length - 1];
    }

    public static String getPathFileName(String path) {
        return getPathFileName(path, "\\\\");
    }

    public static String replacePathFileName(String path, String newFileName) {
        String[] pathArr = path.split("\\\\");
        pathArr[pathArr.length - 1] = newFileName;
        return String.join("\\\\", pathArr);
    }
}
