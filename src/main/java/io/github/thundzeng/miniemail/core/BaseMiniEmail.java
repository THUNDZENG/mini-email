package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.constant.EmailContentTypeEnum;
import io.github.thundzeng.miniemail.util.StringUtils;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 基础邮件发送类。支持继承此类进行扩展
 *
 * @author thundzeng
 */
public abstract class BaseMiniEmail implements MiniEmail {
    private final MimeMessage msg;
    private final MimeMultipart cover;
    private final String fromName;

    public BaseMiniEmail(MimeMessage msg, String fromName) {
        this.msg = msg;
        this.fromName = fromName;
        this.cover = new MimeMultipart("mixed");
    }

    /**
     * 发送给单个收件人。支持主题、文本 or HTML 内容发送
     *
     * @param to 收件人
     * @param subject 主题
     * @param contentType 内容类型
     * @param content 内容
     * @return 发送成功的收件人邮箱
     */
    public String send(String to, String subject, EmailContentTypeEnum contentType, String content) {
        config(subject, to, contentType, content);
        boolean sendSuccess = this.send();
        if (sendSuccess) {
            return to;
        }

        return "";
    }
    /**
     * 发送给多个收件人。支持主题、文本 or HTML 内容发送
     *
     * @param tos 收件人集合
     * @param subject 主题
     * @param contentType 内容类型
     * @param content 内容
     * @return 发送成功的收件人邮箱集合
     */
    public List<String> send(String[] tos, String subject, EmailContentTypeEnum contentType, String content) {
        List<String> sendSuccessToList = new ArrayList<>(tos.length);
        for (int i = 0; i < tos.length; i++) {
            String to = tos[i];
            String sendSuccessTo = this.send(to, subject, contentType, content);
            if (StringUtils.isNotEmpty(sendSuccessTo)) {
                sendSuccessToList.add(sendSuccessTo);
            }
            if (i == 0) {
                try {
                    // 清空抄送邮箱，防止重复发送
                    msg.setHeader("Cc", "");
                    // 清空密送邮箱，防止重复发送
                    msg.setHeader("Bcc", "");
                } catch (MessagingException ignored) {
                }
            }
        }

        return sendSuccessToList;
    }

    private boolean send() {
        boolean sendSuccess = Boolean.FALSE;
        try {
            msg.setSentDate(Calendar.getInstance().getTime());
            Transport.send(msg);
            sendSuccess = Boolean.TRUE;
        } catch (MessagingException ignored) {
            ignored.printStackTrace();
        } finally {
            // fix issue : https://gitee.com/thundzeng/mini-email/issues/I4GS8C
            try {
                clearContentAfterSend();
            } catch (MessagingException ignored) {
            }
        }

        return sendSuccess;
    }

    /**
     * 设置附件
     *
     * @param dataHandler 附件handler
     * @param fileName    附件名称
     */
    public void setDataHandler(DataHandler dataHandler, String fileName) throws MessagingException, UnsupportedEncodingException {
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
    private void config(String subject, String to, EmailContentTypeEnum contentType, String content) {
        try {
            msg.setFrom(new InternetAddress(fromName));

            msg.setSubject(subject, "UTF-8");

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
     */
    private void addRecipient(String recipients, Message.RecipientType recipientType) throws MessagingException {
        if (null != recipients) {
            msg.setRecipients(recipientType, recipients);
        }
    }

    /**
     * 添加抄送人、密抄送人（只能批量添加，若是循环添加会导致只发第一个邮箱）
     *
     * @param recipients    收件人、抄送人、密抄送人数据
     * @param recipientType 发送类型
     */
    public void addRecipients(String[] recipients, Message.RecipientType recipientType) throws MessagingException {
        if (null != recipients && recipients.length > 0) {
            InternetAddress[] parse = InternetAddress.parse(String.join(",", recipients));
            msg.setRecipients(recipientType, parse);
        }
    }

    /**
     * 设置发送的邮件内容
     */
    private void setContent(EmailContentTypeEnum contentType, String content) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(content, contentType.getContentType());
        cover.addBodyPart(bodyPart);
        msg.setContent(cover);
    }

    /**
     * 内容发送成功后，清除发送的内容
     */
    private void clearContentAfterSend() throws MessagingException {
        int count = cover.getCount();
        if (count <= 0) {
            return ;
        }

        while (cover.getCount() > 0) {
            cover.removeBodyPart(0);
        }
        msg.setContent(cover);
    }
}
