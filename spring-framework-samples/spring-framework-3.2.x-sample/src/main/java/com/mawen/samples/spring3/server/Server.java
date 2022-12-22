package com.mawen.samples.spring3.server;

/**
 * 服务器接口
 */
public interface Server {

    /**
     * 启动服务器
     */
    void start();

    /**
     * 关闭服务器
     */
    void stop();

    enum Type {
        HTTP, // HTTP 服务器
        FTP // FTP 服务器
    }

}
