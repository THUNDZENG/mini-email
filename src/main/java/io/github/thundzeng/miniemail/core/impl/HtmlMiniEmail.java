package io.github.thundzeng.miniemail.core.impl;

import io.github.thundzeng.miniemail.core.MiniEmail;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
import java.util.Calendar;

/**
 * html 邮件模板
 *
 * @author thundzeng
 */
public class HtmlMiniEmail extends BaseMiniEmail implements MiniEmail {

    private MimeMessage msg;
    private MimeMultipart cover;

    public HtmlMiniEmail(MimeMessage mimeMessage, String subject, String fromName) {
        super();

        this.msg = mimeMessage;
        cover = new MimeMultipart("mixed");
        config(msg, subject, fromName, null);
    }

    private void setContent(String content) throws MessagingException {
        if (null != content) {
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html; charset=utf-8");
            cover.addBodyPart(bodyPart);
            msg.setContent(cover);
        }
    }

    @Override
    public void send(String to, String content) {
        log.info("[Html邮件] 邮件发送开始------>" + to);
        try {
            config(msg, null, null, to);
            this.setContent(content);
            msg.setSentDate(Calendar.getInstance().getTime());
            Transport.send(msg);
        } catch (MessagingException e) {
            log.warning("[Html邮件] 邮件发送失败：" + to);
        }
        log.info("[Html邮件] 邮件发送结束------>" + to);
    }

    @Override
    public void send(String[] tos, String content) {
        if (tos.length > 0) {
            for (String to : tos) {
                this.send(to, content);
            }
        }
    }

    @Override
    public MiniEmail addAttachment(File file, String fileName) {
        if (null != file && file.isFile()) {
            try {
                cover.addBodyPart(super.createAttachment(file, fileName));
            } catch (MessagingException e) {
                log.warning("[Html邮件] 添加邮件附件文件失败：" + fileName);
            }
        }
        return this;
    }

    @Override
    public MiniEmail addAttachment(URL url, String urlName) {
        if (null != url) {
            try {
                cover.addBodyPart((super.createURLAttachment(url, urlName)));
            } catch (MessagingException e) {
                log.warning("[Html邮件] 添加邮件附件链接失败：" + urlName);
            }
        }
        return this;
    }
}
