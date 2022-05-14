package io.github.thundzeng.miniemail.config;

import io.github.thundzeng.miniemail.constant.SmtpEnum;

import java.math.BigDecimal;

/**
 * 邮件配置项 封装类
 */
public class MailConfig {
	/**
	 * 发件人邮箱
	 */
	private String username;
	/**
	 * 发件人邮箱密码（qq邮箱、163邮箱需要的是邮箱授权码，新浪邮箱直接是邮箱登录密码）
	 */
	private String password;
	/**
	 * 支持的邮箱Host。{@link SmtpEnum}
	 */
	private SmtpEnum mailSmtpHost;
	/**
	 * 是否开启debug
	 */
	private Boolean mailDebug;

	private Boolean mailSmtpAuth;
	private Boolean mailSmtpSslEnable;
	private String mailTransportProtocol;
	private Long mailSmtpTimeout;
	private Integer mailSmtpPort;

	public static MailConfig config(String username, String password){
		return new MailConfig().setUsername(username).setPassword(password);
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public SmtpEnum getMailSmtpHost() {
		return mailSmtpHost;
	}
	public Boolean getMailDebug() {
		return mailDebug;
	}
	public Boolean getMailSmtpAuth() {
		return mailSmtpAuth;
	}
	public Boolean getMailSmtpSslEnable() {
		return mailSmtpSslEnable;
	}
	public String getMailTransportProtocol() {
		return mailTransportProtocol;
	}
	public Long getMailSmtpTimeout() {
		return mailSmtpTimeout;
	}
	public Integer getMailSmtpPort() {
		return mailSmtpPort;
	}

	public MailConfig setUsername(String username) {
		this.username = username;
		return this;
	}
	public MailConfig setPassword(String password) {
		this.password = password;
		return this;
	}
	public MailConfig setMailSmtpHost(SmtpEnum mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
		return this;
	}
	public MailConfig setMailDebug(Boolean mailDebug) {
		this.mailDebug = mailDebug;
		return this;
	}
	public MailConfig setMailSmtpAuth(Boolean mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
		return this;
	}
	public MailConfig setMailSmtpSslEnable(Boolean mailSmtpSslEnable) {
		this.mailSmtpSslEnable = mailSmtpSslEnable;
		return this;
	}
	public MailConfig setMailTransportProtocol(String mailTransportProtocol) {
		this.mailTransportProtocol = mailTransportProtocol;
		return this;
	}
	public MailConfig setMailSmtpTimeout(Long mailSmtpTimeout) {
		this.mailSmtpTimeout = mailSmtpTimeout;
		return this;
	}
	public MailConfig setMailSmtpPort(Integer mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
		return this;
	}
}
