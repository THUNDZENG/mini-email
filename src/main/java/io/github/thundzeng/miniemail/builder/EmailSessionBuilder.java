package io.github.thundzeng.miniemail.builder;

import com.sun.mail.util.MailSSLSocketFactory;
import io.github.thundzeng.miniemail.config.MailConfig;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 初始收件人身份登录session信息
 *
 * @author thundzeng
 */
public class EmailSessionBuilder {
	private Properties props;

	public EmailSessionBuilder(MailConfig config) {
		this.setProperties(config);
	}

	private void setProperties(MailConfig config) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", config.getMailSmtpAuth());
		props.put("mail.smtp.ssl.enable", config.getMailSmtpSslEnable());
		props.put("mail.smtp.starttls.enable", config.getMailSmtpSslEnable());
		if (Boolean.TRUE.equals(config.getMailSmtpSslEnable())) {
			// 简化证书认证，解决发送邮件时出现 ssl 错误
			try {
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				props.put("mail.smtp.ssl.socketFactory", sf);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}

		props.put("mail.transport.protocol", config.getMailTransportProtocol());
		props.put("mail.smtp.timeout", config.getMailSmtpTimeout());
		props.put("mail.smtp.host", config.getMailSmtpHost().getSmtpHost());
		props.put("mail.smtp.port", config.getMailSmtpPort());

		props.setProperty("username", config.getUsername());
		props.setProperty("password", config.getPassword());

		props.put("mail.debug", Boolean.TRUE.equals(config.getMailDebug()) ? "true" : "false");

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
