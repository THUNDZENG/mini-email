package io.github.thundzeng.miniemail.constant;

/**
 * 邮箱协议host枚举，目前支持QQ邮箱、QQ企业邮、网易163邮箱、新浪邮箱
 *
 * @author thundzeng
 */
public enum SmtpEnum {
    /**
     * QQ邮箱
     */
    SMTP_QQ("smtp.qq.com"),
    /**
     * QQ企业邮箱
     */
    SMTP_ENTERPRISE_QQ("smtp.exmail.qq.com"),
    /**
     * 网易邮箱
     */
    SMTP_163("smtp.163.com"),
    /**
     * 新浪邮箱
     */
    SMTP_SINA("smtp.sina.com.cn"),

    ;

    private String smtpHost;

    SmtpEnum(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpHost() {
        return smtpHost;
    }
}
