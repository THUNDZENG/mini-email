package io.github.thundzeng.miniemail.constant;

/**
 * 邮箱协议host枚举
 *
 * @author thundzeng
 */
public enum SmtpHostEnum {
    /**
     * QQ邮箱。需开启smtp，使用授权码作为密码
     */
    SMTP_QQ("smtp.qq.com", 465, 25),
    /**
     * QQ企业邮箱。默认开启了smtp，默认使用邮箱登录密码作为密码。
     */
    SMTP_ENTERPRISE_QQ("smtp.exmail.qq.com", 465, 25),
    /**
     * 网易邮箱。需开启smtp，使用授权码作为密码
     */
    SMTP_163("smtp.163.com", 465, 25),
    /**
     * 新浪邮箱
     */
    SMTP_SINA("smtp.sina.com.cn", 465, 25),
    /**
     * 中国移动邮箱。需开启smtp，使用授权码作为密码
     */
    SMTP_139("smtp.139.com", 465, 25),
    /**
     * 微软邮箱。需开启smtp，使用邮箱登录密码作为密码。mailSmtpSslEnable 需设置 false
     */
    SMTP_OUTLOOK("smtp.office365.com", null, 587),
    /**
     * 阿里云企业邮箱。需开启smtp，默认使用邮箱登录密码作为密码。
     */
    SMTP_ALIBABA("smtp.mxhichina.com", 465, 587),
    ;

    private final String smtpHost;

    private final Integer sslPort;
    /**
     * 非ssl端口号
     */
    private final Integer notSslPort;

    SmtpHostEnum(String smtpHost, Integer sslPort, Integer notSslPort) {
        this.smtpHost = smtpHost;
        this.sslPort = sslPort;
        this.notSslPort = notSslPort;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public Integer getSslPort() {
        return sslPort;
    }

    public Integer getNotSslPort() {
        return notSslPort;
    }
}
