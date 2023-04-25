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
    SMTP_QQ("smtp.qq.com"),
    /**
     * QQ企业邮箱。默认开启了smtp，使用登录密码作为密码
     */
    SMTP_ENTERPRISE_QQ("smtp.exmail.qq.com"),
    /**
     * 网易邮箱。需开启smtp，使用授权码作为密码
     */
    SMTP_163("smtp.163.com"),
    /**
     * 新浪邮箱
     */
    SMTP_SINA("smtp.sina.com.cn"),
    /**
     * 中国移动邮箱。需开启smtp，使用授权码作为密码
     */
    SMTP_139("smtp.139.com"),
    /**
     * 微软邮箱。需开启smtp，使用邮箱登录密码作为密码。mailSmtpSslEnable 需设置 false
     */
    SMTP_OUTLOOK("smtp.office365.com"),
    ;

    private final String smtpHost;

    SmtpHostEnum(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpHost() {
        return smtpHost;
    }
}
