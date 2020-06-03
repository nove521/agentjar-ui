package com.cx.server.web.handler.holder;

import com.cx.agent.ContextManage;
import com.cx.server.ann.Http;
import com.cx.server.web.handler.DefaultHandle;
import com.cx.server.web.handler.ResponseHandler;
import com.cx.server.web.handler.model.RequestDefine;
import com.cx.server.myBeanMnage.MyBeanContent;
import com.cx.server.myBeanMnage.MyBeanFactory;
import com.cx.utils.ClassUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;

import java.lang.reflect.Method;
import java.util.*;

public class GenerateHandle {

    public static ContextHandlerCollection generate() {

        MyBeanFactory factory = ContextManage.getFactory();
        MyBeanContent content = factory.getBeanContent();

        Map<Method, Object> httpAnn = getHttpAnn(content);
        List<Handler> handlerList = new ArrayList<>();
        httpAnn.keySet().forEach(method -> {

            RequestDefine requestDefine = new RequestDefine(method, httpAnn.get(method));

            String url = ClassUtils.getAnnsVal(method.getAnnotations());
            ContextHandler contextHandler = new ContextHandler();
            contextHandler.setContextPath(url);
            contextHandler.setAllowNullPathInfo(true);
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{new ResponseHandler(requestDefine), new DefaultHandle(requestDefine)});
            contextHandler.setHandler(handlers);
            handlerList.add(contextHandler);

        });

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(handlerList.toArray(new Handler[0]));
        return contexts;
    }

    private static Map<Method, Object> getHttpAnn(MyBeanContent content) {
        Map<Method, Object> resultMap = new HashMap<>();
        Map<Class<?>, Object> beans = content.getBeans();
        Set<Class<?>> classes = beans.keySet();
        for (Class<?> aClass : classes) {

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (ClassUtils.equalsAnn(method.getAnnotations(), Http.class)) {
                    resultMap.put(method, beans.get(aClass));
                }
            }
        }
        return resultMap;
    }

}
