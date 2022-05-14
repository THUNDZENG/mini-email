package io.github.thundzeng.miniemail.builder;

import io.github.thundzeng.miniemail.config.MailConfig;

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

	public EmailSessionBuilder(MailConfig config) {
		this.setProperties(config);
	}

	private void setProperties(MailConfig config) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", config.getMailSmtpAuth());
		props.put("mail.smtp.ssl.enable", config.getMailSmtpSslEnable());
		props.put("mail.transport.protocol", config.getMailTransportProtocol());
		props.put("mail.smtp.timeout", config.getMailSmtpTimeout());
		props.put("mail.smtp.port", config.getMailSmtpPort());

		props.setProperty("username", config.getUsername());
		props.setProperty("password", config.getPassword());

		props.put("mail.smtp.host", config.getMailSmtpHost().getSmtpHost());
		props.put("mail.debug", config.getMailDebug() ? "true" : "false");

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
