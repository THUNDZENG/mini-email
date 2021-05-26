package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.constant.EmailContentTypeEnum;
import io.github.thundzeng.miniemail.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;

/**
 * 基础邮件发送类。支持继承此类进行扩展
 *
 * @author thundzeng
 */
public abstract class BaseMiniEmail implements MiniEmail {
    private MimeMessage msg;
    private MimeMultipart cover;
    private String fromName;

    public BaseMiniEmail(MimeMessage msg, String fromName) {
        this.msg = msg;
        this.fromName = fromName;
        this.cover = new MimeMultipart("mixed");
    }

    public void send(String to, String content) {
        send(to, null, EmailContentTypeEnum.TEXT, content);
    }

    public void send(String[] tos, String content) {
        send(tos, null, EmailContentTypeEnum.TEXT, content);
    }

    public void send(String to, String subject, EmailContentTypeEnum contentType, String content) {
        send(new String[]{to}, subject, contentType, content);
    }

    public void send(String[] tos, String subject, EmailContentTypeEnum contentType, String content) {
        try {
            config(subject, tos, contentType, content);
            msg.setSentDate(Calendar.getInstance().getTime());
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public MiniEmail addAttachment(File file, String fileName) throws MessagingException, UnsupportedEncodingException {
        setDataHandler(new DataHandler(new FileDataSource(file)), fileName);
        return this;
    }

    public MiniEmail addAttachment(URL url, String urlName) throws MessagingException, UnsupportedEncodingException {
        setDataHandler(new DataHandler(url), urlName);
        return this;
    }

    public MiniEmail addCarbonCopy(String[] carbonCopies) throws MessagingException {
        return addRecipient(carbonCopies, Message.RecipientType.CC);
    }

    public MiniEmail addBlindCarbonCopy(String[] blindCarbonCopies) throws MessagingException {
        return addRecipient(blindCarbonCopies, Message.RecipientType.BCC);
    }

    /**
     * 设置附件
     *
     * @param dataHandler 附件handler
     * @param fileName    附件名称
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void setDataHandler(DataHandler dataHandler, String fileName) throws MessagingException, UnsupportedEncodingException {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(dataHandler);
        attachmentPart.setFileName(StringUtils.isEmpty(fileName) ? MimeUtility.encodeText(dataHandler.getName()) : fileName);
        cover.addBodyPart(attachmentPart);
    }

    /**
     * 设置邮件主题、收件人、发送内容等信息
     *
     * @param subject     邮件主题
     * @param to          收件人
     * @param contentType 发送内容类型。{@link EmailContentTypeEnum}
     * @param content     发送内容
     */
    private void config(String subject, String[] to, EmailContentTypeEnum contentType, String content) {
        try {
            msg.setFrom(new InternetAddress(fromName));

            if (!StringUtils.isEmpty(subject)) {
                msg.setSubject(subject, "UTF-8");
            }

            addRecipient(to, Message.RecipientType.TO);

            setContent(contentType, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加收件人、抄送人、密抄送人
     *
     * @param recipients    收件人、抄送人、密抄送人数据
     * @param recipientType 发送类型
     * @return MiniEmail
     * @throws MessagingException
     */
    private MiniEmail addRecipient(String[] recipients, Message.RecipientType recipientType) throws MessagingException {
        if (null != recipients && recipients.length > 0) {
            InternetAddress[] parse = InternetAddress.parse(String.join(",", recipients));
            msg.setRecipients(recipientType, parse);
        }

        return this;
    }

    /**
     * 设置发送的邮件内容
     *
     * @param content 邮件内容
     * @throws MessagingException
     */
    private void setContent(EmailContentTypeEnum contentType, String content) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(content, contentType.getContentType());
        cover.addBodyPart(bodyPart);
        msg.setContent(cover);
    }
}
