package io.github.thundzeng.miniemail.builder;

import com.sun.istack.internal.NotNull;
import io.github.thundzeng.miniemail.constant.SmtpEnum;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * 初始收件人身份登录session信息
 *
 * @author thundzeng
 */
public class EmailSessionBuilder {
    private Properties props;

    public EmailSessionBuilder(Properties props, String username, String password, SmtpEnum smtpEnum, boolean debug) {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.port", "465");

        props.setProperty("username", username);
        props.setProperty("password", password);

        props.put("mail.smtp.host", smtpEnum.getSmtpHost());
        props.put("mail.debug", debug ? "true" : "false");

        this.props = props;
    }

    public Session parseSession() {
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getProps("username"), getProps("password"));
            }
        });
    }

    public String getProps(String key) {
        return props.getProperty(key);
    }
}
