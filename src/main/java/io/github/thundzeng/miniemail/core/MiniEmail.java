package io.github.thundzeng.miniemail.core;

import java.io.File;
import java.net.URL;

/**
 * 邮件操作接口
 *
 * @author thundzeng
 */
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
     * 添加附件文件（本地文件）
     *
     * @param file     附件文件
     * @param fileName 附件文件别名
     * @return MiniEmail
     */
    MiniEmail addAttachment(File file, String fileName);

    /**
     * 添加附件文件（网络链接文件）
     *
     * @param url     附件链接
     * @param urlName 附件链接别名
     * @return MiniEmail
     */
    MiniEmail addAttachment(URL url, String urlName);

    /**
     * 添加邮件抄送人
     *
     * @param carbonCopies 抄送邮箱
     * @return MiniEmail
     */
    MiniEmail addCarbonCopy(String[] carbonCopies);
}
