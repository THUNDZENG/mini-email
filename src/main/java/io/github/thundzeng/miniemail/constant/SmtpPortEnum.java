package io.github.thundzeng.miniemail.constant;

import java.util.Arrays;
import java.util.Optional;

/**
 * 邮件端口号枚举
 *
 * @author thundzeng
 * @since 2.1.2
 */
public enum SmtpPortEnum {
    /**
     * QQ邮箱
     */
    SMTP_QQ(465, 25),
    /**
     * QQ企业邮箱
     */
    SMTP_ENTERPRISE_QQ(465, 25),
    /**
     * 网易邮箱
     */
    SMTP_163(465, 25),
    /**
     * 新浪邮箱
     */
    SMTP_SINA(465, 25),
    /**
     * 中国移动邮箱
     */
    SMTP_139(465, 25),
    /**
     * 微软邮箱
     */
    SMTP_OUTLOOK(null, 587),
    ;
    /**
     * ssl端口号
     */
    private final Integer sslPort;
    /**
     * 非ssl端口号
     */
    private final Integer notSslPort;

    SmtpPortEnum(Integer sslPort, Integer notSslPort) {
        this.sslPort = sslPort;
        this.notSslPort = notSslPort;
    }

    public Integer getSslPort() {
        return sslPort;
    }

    public Integer getNotSslPort() {
        return notSslPort;
    }

    /**
     * 根据 SmtpHostEnum 去匹配对应的元素
     * @author thundzeng
     * @since 2.1.2
     * @return SmtpPortEnum
     */
    public static SmtpPortEnum getBySmtpHost(SmtpHostEnum smtpHostEnum) {
        Optional<SmtpPortEnum> optional = Arrays.stream(values()).filter(e -> e.name().equals(smtpHostEnum.name())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }
}
