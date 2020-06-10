package com.cx.utils;

import com.cx.model.InnerObject;
import com.cx.model.JvmException;
import com.cx.model.Options;
import com.sun.tools.attach.*;
import org.yx.util.S;
import org.yx.util.StringUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class VMutils {

    public static final String JAR_PATH;

    public static final AtomicInteger CURRENT_PORT = new AtomicInteger(18888);

    private static final Map<String, List<InnerObject>> jvmInfosMap = new ConcurrentHashMap<>();

    static {
        JAR_PATH = Objects.requireNonNull(VMutils.class.getClassLoader().getResource("agentjar-1.0.jar")).getPath().substring(1);
//        JAR_PATH = "D:/project/xuexi/TestHotUpdate/agentjar/target/agentjar-1.0.jar";
    }

    public static int generatePort() {
        return CURRENT_PORT.incrementAndGet();
    }

    public static List<Map<String, String>> getAllJVMs() {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        return transform(list);
    }

    public static VirtualMachineDescriptor getJVM(String id) {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor descriptor : list) {
            if (id.equals(descriptor.id())) {
                return descriptor;
            }
        }
        return null;
    }

    public static void launch(VirtualMachineDescriptor vm, String jarPath) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(vm);
        virtualMachine.loadAgent(jarPath, S.json.toJson(Collections.emptyList()));
        virtualMachine.detach();
    }

    public static void launch(VirtualMachineDescriptor vm, String jarPath, Options opt) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach(vm);
        virtualMachine.loadAgent(jarPath, S.json.toJson(opt));
        virtualMachine.detach();
    }

    private static List<Map<String, String>> transform(List<VirtualMachineDescriptor> list) {
        return list.stream()
                .filter(item -> StringUtil.isNotEmpty(item.displayName()) && !"com.cx.client.ClientMain".equals(item.displayName()))
                .map(item -> {
                    Map<String, String> newItem = new HashMap<>();
                    newItem.put("id", item.id());
                    newItem.put("name", item.displayName());
                    return newItem;
                }).collect(Collectors.toList());
    }

    public static void joinProject(String id) throws JvmException {
        VirtualMachineDescriptor jvM = VMutils.getJVM(id);
        if (Objects.isNull(jvM)) {
            throw new JvmException("未找到相应的jvm，可能目标已关闭，请重新请求查询");
        }

        try {
            VMutils.launch(jvM, VMutils.JAR_PATH);
        } catch (IOException | AttachNotSupportedException | AgentLoadException | AgentInitializationException e) {
            e.printStackTrace();
            throw new JvmException("启动注入失败");
        }
    }

}
