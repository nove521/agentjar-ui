package com.cx.mode;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.Map;

/**
 * JVM 简单信息
 */
public class JvmSimpleInfo {

    private String name;
    private String specName;
    private String classPath;
    private String managementSpecVersion;
    private String specVendor;
    private Date startTime;
    private long uptime;
    private String libraryPath;
    private Map<String, String> systemProperties;

    public void fill() {
        RuntimeMXBean mxb = ManagementFactory.getRuntimeMXBean();
        name = mxb.getName();
        specName = mxb.getSpecName();
        classPath = mxb.getClassPath(); // 卧槽，这里就可以获取到classPath
        managementSpecVersion = mxb.getManagementSpecVersion();
        specVendor = mxb.getSpecVendor();
        startTime = new Date(mxb.getStartTime());
        uptime = mxb.getUptime();
        systemProperties = mxb.getSystemProperties();
        libraryPath = mxb.getLibraryPath();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getManagementSpecVersion() {
        return managementSpecVersion;
    }

    public void setManagementSpecVersion(String managementSpecVersion) {
        this.managementSpecVersion = managementSpecVersion;
    }

    public String getSpecVendor() {
        return specVendor;
    }

    public void setSpecVendor(String specVendor) {
        this.specVendor = specVendor;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public void setLibraryPath(String libraryPath) {
        this.libraryPath = libraryPath;
    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(Map<String, String> systemProperties) {
        this.systemProperties = systemProperties;
    }
}
