package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.config.MailConfig;
import io.github.thundzeng.miniemail.constant.SmtpHostEnum;
import io.github.thundzeng.miniemail.exception.ParameterException;
import io.github.thundzeng.miniemail.util.StringUtils;

import java.util.Objects;

/**
 * 建造 {@link MiniEmail} 实例。
 *
 * @author thundzeng
 */
public class MiniEmailFactoryBuilder {

	/**
	 * 建造 MiniEmailFactory
	 * 于 V2.1.2 标注废弃，下版本会删除该方法。使用：{@link #build(MailConfig)} 代替
	 *
	 * @param debug    是否开启debug。
	 * @param username 发件人邮箱。
	 * @param password 发件人邮箱密码（qq邮箱、163邮箱需要的是邮箱授权码，新浪邮箱直接是邮箱登录密码）。
	 * @param smtpHostEnum 支持的邮箱Host。{@link SmtpHostEnum}
	 * @return MiniEmailFactory
	 */
	@Deprecated
	public MiniEmailFactory build(boolean debug, String username, String password, SmtpHostEnum smtpHostEnum) {
		MailConfig config = MailConfig.config(username, password)
				.setMailSmtpHost(smtpHostEnum)
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
		if (StringUtils.isEmpty(config.getSmtpHost())) {
			throw new ParameterException("请填写邮箱Host");
		}
		if (Objects.isNull(config.getSmtpPort())) {
			throw new ParameterException("请填写邮箱Port");
		}
	}
}
