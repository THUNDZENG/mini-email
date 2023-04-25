package io.github.thundzeng.miniemail.config;

import io.github.thundzeng.miniemail.constant.SmtpHostEnum;

/**
 * 邮件配置项 封装类
 *
 * @author thundzeng
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
     * 发件人定制别名。
     */
    private String senderNickname;
    /**
     * 支持的邮箱Host。{@link SmtpHostEnum}
     */
    private SmtpHostEnum mailSmtpHost;
    /**
     * 是否开启debug。默认false
     */
    private Boolean mailDebug = Boolean.FALSE;
    /**
     * 自定义 MiniEmail实现类路径。类必须继承 BaseMiniEmail
     */
    private String customMiniEmail;
    /**
     * 开启账号密码鉴权。默认true
     */
    private Boolean mailSmtpAuth = Boolean.TRUE;
    /**
     * 开启SSL。默认true
     */
    private Boolean mailSmtpSslEnable = Boolean.TRUE;
    /**
     * 超时时间。默认10s
     */
    private Long mailSmtpTimeout = 10000L;

    public static MailConfig config(String username, String password) {
        return new MailConfig().setUsername(username).setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public SmtpHostEnum getMailSmtpHost() {
        return mailSmtpHost;
    }

    public Boolean getMailDebug() {
        return mailDebug;
    }

    public String getCustomMiniEmail() {
        return customMiniEmail;
    }

    public Boolean getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public Boolean getMailSmtpSslEnable() {
        return mailSmtpSslEnable;
    }

    public Long getMailSmtpTimeout() {
        return mailSmtpTimeout;
    }

    public MailConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public MailConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public MailConfig setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
        return this;
    }

    public MailConfig setMailSmtpHost(SmtpHostEnum mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
        return this;
    }

    public MailConfig setMailDebug(Boolean mailDebug) {
        this.mailDebug = mailDebug;
        return this;
    }

    public MailConfig setCustomMiniEmail(String customMiniEmail) {
        this.customMiniEmail = customMiniEmail;
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

    public MailConfig setMailSmtpTimeout(Long mailSmtpTimeout) {
        this.mailSmtpTimeout = mailSmtpTimeout;
        return this;
    }

}
