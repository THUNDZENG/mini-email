package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.constant.EmailContentTypeEnum;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * 邮件操作接口
 *
 * @author thundzeng
 */
public interface MiniEmail {

    /**
     * 发送纯文本邮件给指定用户
     *
     * @param to      收件人
     * @param content 发送内容
     * @since 1.0.0
     */
    void send(String to, String content);

    /**
     * 批量发送纯文本邮件给指定用户
     *
     * @param tos     收件人
     * @param content 发送内容
     * @since 1.0.0
     */
    void send(String[] tos, String content);

    /**
     * 发送给指定用户
     *
     * @param to          接收邮箱
     * @param subject     邮件主题
     * @param contentType 邮件内容格式类型
     * @param content     发送内容
     * @since 1.3.0
     */
    void send(String to, String subject, EmailContentTypeEnum contentType, String content);

    /**
     * 批量发送给指定用户
     *
     * @param tos         接收邮箱
     * @param subject     邮件主题
     * @param contentType 邮件内容格式类型
     * @param content     发送内容s
     * @since 1.3.0
     */
    void send(String[] tos, String subject, EmailContentTypeEnum contentType, String content);

    /**
     * 添加附件文件（本地文件）
     *
     * @param file     附件文件
     * @param fileName 附件文件别名
     * @return MiniEmail
     * @since 1.0.0
     */
    MiniEmail addAttachment(File file, String fileName) throws MessagingException, UnsupportedEncodingException;

    /**
     * 添加附件文件（网络链接文件）
     *
     * @param url     附件链接
     * @param urlName 附件链接别名
     * @return MiniEmail
     * @since 1.0.0
     */
    MiniEmail addAttachment(URL url, String urlName) throws MessagingException, UnsupportedEncodingException;

    /**
     * 添加邮件抄送人
     *
     * @param carbonCopies 抄送邮箱
     * @return MiniEmail
     * @since 1.1.1
     */
    MiniEmail addCarbonCopy(String[] carbonCopies) throws MessagingException;

    /**
     * 添加邮件密抄送人
     *
     * @param blindCarbonCopies 密抄邮箱
     * @return MiniEmail
     * @since 1.3.0
     */
    MiniEmail addBlindCarbonCopy(String[] blindCarbonCopies) throws MessagingException;
}
