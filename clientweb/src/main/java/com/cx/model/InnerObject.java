package com.cx.model;

import com.cx.utils.VMutils;

import java.time.LocalDateTime;

/**
 * 注入jvm的 详情
 */
public class InnerObject {

    // 远程开启的端口
    private String port;

    // 远程ip
    private String ip;

    // 注入时间
    private LocalDateTime joinTime;

    // 注入的用户
    private String userId;

    public InnerObject(String userId) {
        this.userId = userId;
        this.port = String.valueOf(VMutils.generatePort());
        this.ip = "127.0.0.1";
        this.joinTime = LocalDateTime.now();
    }

    public InnerObject(String port, String ip, LocalDateTime joinTime, String userId) {
        this.port = port;
        this.ip = ip;
        this.joinTime = joinTime;
        this.userId = userId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
