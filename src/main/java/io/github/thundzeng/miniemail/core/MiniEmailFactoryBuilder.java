package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.builder.EmailSessionBuilder;
import io.github.thundzeng.miniemail.constant.SmtpEnum;
import io.github.thundzeng.miniemail.core.defaults.DefaultMiniEmailFactory;
import io.github.thundzeng.miniemail.exception.ParameterException;
import io.github.thundzeng.miniemail.util.StringUtils;

import java.util.Properties;

/**
 * 建造 {@link MiniEmail} 实例。
 *
 * @author thundzeng
 */
public class MiniEmailFactoryBuilder {

    /**
     * 建造 MiniEmailFactory
     *
     * @param debug    是否开启debug。
     * @param username 发件人邮箱。
     * @param password 发件人邮箱密码（qq邮箱、163邮箱需要的是邮箱授权码，新浪邮箱直接是邮箱登录密码）。
     * @param smtpEnum 支持的邮箱Host。{@link SmtpEnum}
     * @return MiniEmailFactory
     */
    public MiniEmailFactory build(boolean debug, String username, String password, SmtpEnum smtpEnum) {
        this.checkParameter(username, password, smtpEnum);

        // init session
        Properties props = new Properties();
        EmailSessionBuilder emailSessionBuilder = new EmailSessionBuilder(props, username, password, smtpEnum, debug);

        return new DefaultMiniEmailFactory(emailSessionBuilder);
    }

    /**
     * 建造 MiniEmailFactory
     *
     * @param username 发件人邮箱。
     * @param password 发件人邮箱密码（qq邮箱、163邮箱需要的是邮箱授权码，新浪邮箱直接是邮箱登录密码）。
     * @param smtpEnum 支持的邮箱Host。{@link SmtpEnum}
     * @return MiniEmailFactory
     */
    public MiniEmailFactory build(String username, String password, SmtpEnum smtpEnum) {
        return this.build(false, username, password, smtpEnum);
    }

    /**
     * 参数检查
     */
    private void checkParameter(String username, String password, SmtpEnum smtpEnum) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ParameterException("请填写完整的收件人信息");
        }
        if (null == smtpEnum) {
            throw new ParameterException("请选择邮件Host");
        }
    }
}
