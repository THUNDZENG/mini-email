package io.github.thundzeng.miniemail.core.impl;


import io.github.thundzeng.miniemail.core.MiniEmail;
import io.github.thundzeng.miniemail.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 基础邮件模板
 *
 * @author thundzeng
 */
public abstract class BaseMiniEmail {

    protected static Logger log;

    static {
        log = Logger.getLogger("default");
        // 日志输出默认关闭。需要源码调试时，可设置为 Level.INFO
        log.setLevel(Level.OFF);
    }

    private MimeMessage msg;
    private MimeMultipart cover;

    public BaseMiniEmail(MimeMessage msg, MimeMultipart cover) {
        this.msg = msg;
        this.cover = cover;
    }

    public void config(String subject, String fromName, String to) {
        try {
            if (null != subject) {
                msg.setSubject(subject, "UTF-8");
            }
            if (null != fromName) {
                msg.setFrom(new InternetAddress(fromName));
            }
            if (null != to) {
                msg.setRecipients(Message.RecipientType.TO, to);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 考虑到各邮件类型的不同，此方法留给子类去重写
     *
     * @param content 发送内容
     * @throws MessagingException
     */
    protected abstract void addContent(String content) throws MessagingException;

    public void setContent(MimeBodyPart bodyPart) throws MessagingException {
        // fix：群发时，内容会多次拼接
        int count = cover.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                cover.removeBodyPart(i);
            }
        }
        cover.addBodyPart(bodyPart);
        msg.setContent(cover);
    }

    protected MiniEmail addAttachment(MiniEmail miniEmail, File file, String fileName) throws MessagingException {
        FileDataSource fds = new FileDataSource(file);
        MimeBodyPart attachmentPart = new MimeBodyPart();
        try {
            attachmentPart.setDataHandler(new DataHandler(fds));
            attachmentPart.setFileName(StringUtils.isEmpty(fileName) ? MimeUtility.encodeText(fds.getName()) : MimeUtility.encodeText(fileName));
        } catch (Exception e) {
            log.warning("添加邮件附件文件失败：" + fileName);
        }
        cover.addBodyPart(attachmentPart);

        return miniEmail;
    }

    protected MiniEmail addUrlAttachment(MiniEmail miniEmail, URL url, String urlName) throws MessagingException {
        DataHandler dataHandler = new DataHandler(url);
        MimeBodyPart attachmentPart = new MimeBodyPart();
        try {
            attachmentPart.setDataHandler(dataHandler);
            attachmentPart.setFileName(StringUtils.isEmpty(urlName) ? MimeUtility.encodeText(dataHandler.getName()) : urlName);
        } catch (Exception e) {
            log.warning("添加邮件附件链接失败：" + urlName);
        }
        cover.addBodyPart(attachmentPart);

        return miniEmail;
    }

    public void send(String to, String content) {
        log.info("邮件发送开始------>" + to);
        try {
            config(null, null, to);
            addContent(content);
            msg.setSentDate(Calendar.getInstance().getTime());
            Transport.send(msg);
        } catch (MessagingException e) {
            log.warning("邮件发送失败：" + to);
        }
        log.info("邮件发送结束------>" + to);
    }

    public void send(String[] tos, String content) {
        if (tos.length > 0) {
            for (String to : tos) {
                send(to, content);
            }
        }
    }

    public MiniEmail addCarbonCopy(MiniEmail miniEmail, String[] carbonCopies) throws MessagingException {
        if (null != carbonCopies && carbonCopies.length > 0) {
            InternetAddress[] parse = InternetAddress.parse(String.join(",", carbonCopies));
            msg.setRecipients(Message.RecipientType.CC, parse);
        }

        return miniEmail;
    }

}
