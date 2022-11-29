package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.config.MailConfig;
import io.github.thundzeng.miniemail.constant.SmtpEnum;
import io.github.thundzeng.miniemail.exception.ParameterException;
import io.github.thundzeng.miniemail.util.StringUtils;

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
		MailConfig config = MailConfig.config(username, password)
				.setMailSmtpHost(smtpEnum)
				.setMailDebug(debug);

		return this.build(config);
	}

	/**
	 * 建造 MiniEmailFactory
	 *
	 * @param config 配置参数。
	 * @return MiniEmailFactory
	 */
	public MiniEmailFactory build(MailConfig config) {
		this.checkParameter(config);

		return new DefaultMiniEmailFactory(config);
	}

	/**
	 * 参数检查
	 *
	 * @param config 配置参数。
	 */
	private void checkParameter(MailConfig config) {
		if (StringUtils.isEmpty(config.getUsername()) || StringUtils.isEmpty(config.getPassword())) {
			throw new ParameterException("请填写完整的收件人信息");
		}
		if (null == config.getMailSmtpHost()) {
			throw new ParameterException("请选择邮件Host");
		}
	}
}
