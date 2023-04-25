package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.builder.EmailSessionBuilder;
import io.github.thundzeng.miniemail.config.MailConfig;
import io.github.thundzeng.miniemail.util.StringUtils;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * 默认 MiniEmailFactory
 *
 * @author thundzeng
 */
public class DefaultMiniEmailFactory implements MiniEmailFactory {

    private final EmailSessionBuilder sessionBuilder;
    private final String finalSenderName;
    private final String customMiniEmailPath;

    public DefaultMiniEmailFactory(MailConfig config) {
        // init session
        this.sessionBuilder = new EmailSessionBuilder(config);
        this.finalSenderName = this.constructFinalSenderName(config.getSenderNickname(), config.getUsername());
        this.customMiniEmailPath = config.getCustomMiniEmail();
    }

    /**
     * 发件人昵称 构建
     *
     * @param senderNickname 自定义昵称
     * @param username       发件人邮箱
     * @return String 处理后的发件人昵称
     */
    private String constructFinalSenderName(String senderNickname, String username) {
        boolean notDealName = StringUtils.isEmpty(senderNickname) || senderNickname.equals(username);
        if (notDealName) {
            return username;
        }

        // 如果有定制发件人昵称，此处作处理
        try {
            senderNickname = MimeUtility.encodeText(senderNickname) + " <" + username + ">";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("senderNickname 解析失败，重新使用 username 作为发件人别名。");
        }

        // 若定制发件人定制昵称为空，则使用 username 作为发件人别名
        return StringUtils.isEmpty(senderNickname) ? username : senderNickname;
    }

    private MiniEmail init(Class<? extends BaseMiniEmail> clazz) {
        BaseMiniEmail baseMiniEmail = null;
        try {
            baseMiniEmail = clazz.getDeclaredConstructor(MimeMessage.class, String.class)
                    .newInstance(new MimeMessage(sessionBuilder.parseSession()), finalSenderName);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }

        if (null == baseMiniEmail) {
            throw new RuntimeException("初始化 MiniEmail 失败，请检查类路径正确以及是否继承 BaseMiniEmail。");
        }

        return baseMiniEmail;
    }

    @Override
    public MiniEmail init() {
        if (StringUtils.isEmpty(customMiniEmailPath)) {
            return this.init(DefaultMiniEmail.class);
        }

        // 加载自定义实现类
        Class<BaseMiniEmail> customClass = null;
        try {
            customClass = (Class<BaseMiniEmail>) DefaultMiniEmailFactory.class
                    .getClassLoader()
                    .loadClass(customMiniEmailPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null == customClass ? null : this.init(customClass);
    }

}
