package com.mawen.samples.spring3.server;

import org.springframework.stereotype.Component;

/**
 * FTP 服务器
 */
@Component // 根据 ImportSelector 的契约，请确保是实现为 Spring 组件
public class FTPServer implements Server{

    @Override
    public void start() {
        System.out.println("FTP 服务器启动中...");
    }

    @Override
    public void stop() {
        System.out.println("FTP 服务器关闭中...");
    }
}
