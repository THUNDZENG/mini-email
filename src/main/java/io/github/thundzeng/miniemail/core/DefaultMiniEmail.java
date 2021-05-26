package io.github.thundzeng.miniemail.core;


import io.github.thundzeng.miniemail.constant.EmailContentTypeEnum;
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
public class DefaultMiniEmail extends BaseMiniEmail {
    protected static Logger log;

    static {
        log = Logger.getLogger("default");
        // 日志输出默认关闭。需要源码调试时，可设置为 Level.INFO
        log.setLevel(Level.OFF);
    }

    public DefaultMiniEmail(MimeMessage msg, String fromName) {
        super(msg, fromName);
    }


    @Override
    public void send(String[] tos, String subject, EmailContentTypeEnum contentType, String content) {
        log.info("邮件发送开始------>" + tos);
        super.send(tos, subject, contentType, content);
        log.info("邮件发送结束------>" + tos);
    }

}
