package com.cx.javaCompiler;

import com.cx.utils.FileUtils;
import com.cx.utils.JarTool;
import com.cx.utils.StrUtils;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.state.ClassFileSourceImpl;
import org.benf.cfr.reader.util.getopt.OptionsImpl;

import java.io.File;
import java.util.*;

/**
 * 反编译工具类
 */
public class Decompiler {

    /**
     * @param classFilePath
     * @param methodName
     * @return
     */
    public static String decompile(String classFilePath, String methodName) {
        final StringBuilder result = new StringBuilder(8192);

        OutputSinkFactory mySink = new OutputSinkFactory() {
            @Override
            public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                return Arrays.asList(SinkClass.STRING, SinkClass.DECOMPILED, SinkClass.DECOMPILED_MULTIVER,
                        SinkClass.EXCEPTION_MESSAGE);
            }

            @Override
            public <T> Sink<T> getSink(final SinkType sinkType, SinkClass sinkClass) {
                return new Sink<T>() {
                    @Override
                    public void write(T sinkable) {
                        // skip message like: Analysing type demo.MathGame
                        if (sinkType == SinkType.PROGRESS) {
                            return;
                        }
                        result.append(sinkable);
                    }
                };
            }
        };

        HashMap<String, String> options = new HashMap<String, String>();
        /**
         * @see org.benf.cfr.reader.util.MiscConstants.Version.getVersion() Currently,
         *      the cfr version is wrong. so disable show cfr version.
         */
        options.put("showversion", "false");
        if (!StrUtils.isBlank(methodName)) {
            options.put("methodname", methodName);
        }

        CfrDriver.Builder builder = new CfrDriver.Builder();

        if (classFilePath.contains(".jar!/")) {

            String jarPath = classFilePath.substring(0, classFilePath.indexOf(JarTool.JAR_SUFFIX) + 4);
            classFilePath = classFilePath.substring(classFilePath.indexOf(JarTool.JAR_SUFFIX) + 6);
            ClassFileSource classFileSource = new ClassFileSourceImpl(new OptionsImpl(options));

            classFilePath = classFilePath.replace("!","");

            System.out.println(jarPath);
            System.out.println(classFilePath);

            classFileSource.addJar(jarPath);

            builder.withClassFileSource(classFileSource);
        }

        CfrDriver driver = builder.withOptions(options).withOutputSink(mySink).build();
        List<String> toAnalyse = new ArrayList<>();
        toAnalyse.add(classFilePath);
        driver.analyse(toAnalyse);

        if ("null".equals(result.toString())) {
            return null;
        }

        return result.toString().replace("/*\n" +
                " * Decompiled with CFR.\n" +
                " */\n", "");
    }

    public static void main(String[] args) {
//        String decompile = decompile("org/yx/http/kit/HttpTypePredicate.class", null);
//        System.out.println(decompile);
        System.out.println(Decompiler.class.getProtectionDomain().getCodeSource().getLocation());
//        String path = "/D:/project/xuexi/TestHotUpdate/clientweb/target/classes/agentjar-1.0.jar!/org/eclipse/jetty/server/HttpChannel.class";
        String path = "file:/D:/project/simple/target/simple-0.0.1.jar!/BOOT-INF/classes/com/example/simple/controller/HelloController.class";

//        String code = decompile("/D:/project/simple/target/simple-0.0.1.jar!/BOOT-INF/classes!/com/example/simple/controller/HelloController.class",null);
        String code = decompile(path, null);
        System.out.println(code);
    }
}
