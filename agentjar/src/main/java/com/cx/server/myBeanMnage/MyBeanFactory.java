package com.cx.server.myBeanMnage;

import com.cx.server.ann.Obj;
import com.cx.utils.ClassUtils;
import com.cx.utils.FileUtils;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MyBeanFactory {

    private MyBeanContent beanContent;

    public MyBeanContent getBeanContent() {
        if (Objects.isNull(beanContent)) {
            throw new NullPointerException();
        }
        return beanContent;
    }

    /**
     * 初始化bean
     *
     * @return
     */
    public boolean init() {

        MyBeanContent content = new MyBeanContent();
        String packName = getBeanPath();
        String path = packName.replace(".", "/");
        URL url = getClazz().getClassLoader().getResource(path);
        if (Objects.isNull(url)) {
            return false;
        }
        try {
            List<String> list = FileUtils.getDirAllClassPath(url.toURI(), packName);
            List<Class<?>> classList = ClassUtils.getClassByPackNames(list);
            classList = ClassUtils.filterClassByAnn(classList, Obj.class);
            ClassUtils.generateBeans(classList, content.getBeans());
            beanContent = content;
            return true;
        } catch (URISyntaxException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取扫描的bean路径。默认当前目录的上一级
     *
     * @return
     */
    private static String getBeanPath() {
        String path = MyBeanFactory.class.toString();
        path = path.substring(6, path.lastIndexOf("."));
        path = path.substring(0, path.lastIndexOf("."));
        return path;
    }

    private static Class<MyBeanFactory> getClazz() {
        return MyBeanFactory.class;
    }

    public void clear() {
        beanContent.clear();
    }
}
