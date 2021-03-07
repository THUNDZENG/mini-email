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
public class BaseMiniEmail {

    protected static Logger log;

    static {
        log = Logger.getLogger("default");
        log.setLevel(Level.INFO);
    }

    private MimeMessage msg;
    private MimeMultipart cover;

    public BaseMiniEmail(MimeMessage msg, MimeMultipart cover) {
        this.msg = msg;
        this.cover = cover;
    }

    public void config(String subject, String fromName, String[] to, Message.RecipientType recipientType) {
        try {
            if (null != subject) {
                msg.setSubject(subject, "UTF-8");
            }
            if (null != fromName) {
                msg.setFrom(new InternetAddress(fromName));
            }
            if (null != to) {
                InternetAddress[] parse = InternetAddress.parse(String.join(",", to));
                msg.setRecipients(recipientType, parse);
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
    public void addContent(String content) throws MessagingException {
    }

    public void setContent(MimeBodyPart bodyPart) throws MessagingException {
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
        send(new String[]{to}, content);
    }

    public void send(String[] tos, String content) {
        if (tos.length > 0) {
            String to = String.join(",", tos);
            log.info("邮件发送开始------>" + to);
            try {
                config(null, null, tos, Message.RecipientType.TO);
                addContent(content);
                msg.setSentDate(Calendar.getInstance().getTime());
                Transport.send(msg);
            } catch (MessagingException e) {
                log.warning("邮件发送失败：" + to);
            }
            log.info("邮件发送结束------>" + to);
        }
    }

    public MiniEmail addCarbonCopy(MiniEmail miniEmail, String[] carbonCopies) throws MessagingException {
        if (null != carbonCopies && carbonCopies.length > 0) {
            config(null, null, carbonCopies, Message.RecipientType.CC);
        }

        return miniEmail;
    }

}
