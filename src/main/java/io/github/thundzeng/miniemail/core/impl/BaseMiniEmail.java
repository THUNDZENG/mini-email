package io.github.thundzeng.miniemail.core.impl;

import jetbrick.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.net.URL;
import java.util.logging.Logger;
import java.util.logging.Level;

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

    public BaseMiniEmail() {
    }

    public void config(MimeMessage msg, String subject, String fromName, String to) {
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

    protected MimeBodyPart createAttachment(File file, String fileName) {
        FileDataSource fds = new FileDataSource(file);
        MimeBodyPart attachmentPart = new MimeBodyPart();
        try {
            attachmentPart.setDataHandler(new DataHandler(fds));
            attachmentPart.setFileName(StringUtils.isBlank(fileName) ? MimeUtility.encodeText(fds.getName()) : MimeUtility.encodeText(fileName));
        } catch (Exception e) {
            log.warning("添加邮件附件文件失败：" + fileName);
        }
        return attachmentPart;
    }

    protected MimeBodyPart createURLAttachment(URL url, String urlName) {
        DataHandler dataHandler = new DataHandler(url);
        MimeBodyPart attachmentPart = new MimeBodyPart();
        try {
            attachmentPart.setDataHandler(dataHandler);
            attachmentPart.setFileName(StringUtils.isBlank(urlName) ? MimeUtility.encodeText(dataHandler.getName()) : urlName);
        } catch (Exception e) {
            log.warning("添加邮件附件链接失败：" + urlName);
        }
        return attachmentPart;
    }

}
