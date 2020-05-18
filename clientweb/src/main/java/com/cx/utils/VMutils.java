package com.cx.utils;

import com.sun.tools.attach.*;
import org.yx.util.S;
import org.yx.util.StringUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class VMutils {

    public static final String JAR_PATH;

    static {
        JAR_PATH = Objects.requireNonNull(VMutils.class.getClassLoader().getResource("agentjar-1.0.jar")).getPath().substring(1);
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

}
