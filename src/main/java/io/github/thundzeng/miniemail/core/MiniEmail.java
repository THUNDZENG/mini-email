package io.github.thundzeng.miniemail.core;

import java.io.File;
import java.net.URL;

public interface MiniEmail {

    /**
     * 发送给指定用户
     *
     * @param to      接收邮箱
     * @param content 发送内容
     */
    void send(String to, String content);

    /**
     * 批量发送给指定用户
     *
     * @param tos     接收邮箱
     * @param content 发送内容s
     */
    void send(String[] tos, String content);

    /**
     * 添加附件文件
     *
     * @param file
     * @param fileName
     * @return MiniEmail
     */
    MiniEmail addAttachment(File file, String fileName);

    /**
     * 添加附件链接
     *
     * @param url
     * @param urlName
     * @return MiniEmail
     */
    MiniEmail addAttachment(URL url, String urlName);
}
