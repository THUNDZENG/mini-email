package io.github.thundzeng.miniemail.constant;

/**
 * 邮箱host枚举，目前支持QQ邮箱、QQ企业邮、网易163邮箱、新浪邮箱
 *
 * @author thundzeng
 */
public enum SmtpEnum {
    SMTP_QQ("smtp.qq.com"),
    SMTP_ENTERPRISE_QQ("smtp.exmail.qq.com"),
    SMTP_163("smtp.163.com"),
    SMTP_SINA("smtp.sina.com.cn"),

    ;

    SmtpEnum(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    private String smtpHost;

    public String getSmtpHost() {
        return smtpHost;
    }
}
