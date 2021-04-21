package io.github.thundzeng.miniemail.core.impl;

import io.github.thundzeng.miniemail.core.MiniEmail;
import io.github.thundzeng.miniemail.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.net.URL;

/**
 * 文本邮件模板
 *
 * @author thundzeng
 */
public class TextMiniEmail extends BaseMiniEmail implements MiniEmail {
    public TextMiniEmail(MimeMessage mimeMessage, String subject, String fromName) {
        super(mimeMessage, new MimeMultipart("mixed"));
        config(subject, fromName, null);
    }

    @Override
    public void addContent(String content) throws MessagingException {
        if (!StringUtils.isEmpty(content)) {
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(content);
            setContent(bodyPart);
        }
    }

    @Override
    public MiniEmail addAttachment(File file, String fileName) {
        if (null != file && file.isFile()) {
            try {
                addAttachment(this, file, fileName);
            } catch (MessagingException e) {
                log.warning("[Text邮件] 添加邮件附件文件失败：" + fileName);
            }
        }
        return this;
    }

    @Override
    public MiniEmail addAttachment(URL url, String urlName) {
        if (null != url) {
            try {
                addUrlAttachment(this, url, urlName);
            } catch (MessagingException e) {
                log.warning("[Text邮件] 添加邮件附件链接失败：" + urlName);
            }
        }
        return this;
    }

    @Override
    public MiniEmail addCarbonCopy(String[] carbonCopies) {
        try {
            return addCarbonCopy(this, carbonCopies);
        } catch (MessagingException e) {
            log.warning("[Text邮件] 添加抄送邮件失败：" + carbonCopies);
        }
        return this;
    }

}
