package io.github.thundzeng.miniemail.core;

import io.github.thundzeng.miniemail.builder.EmailSessionBuilder;
import io.github.thundzeng.miniemail.util.StringUtils;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * 默认 MiniEmailFactory
 *
 * @author thundzeng
 */
public class DefaultMiniEmailFactory implements MiniEmailFactory {

    private EmailSessionBuilder sessionBuilder;

    public DefaultMiniEmailFactory(EmailSessionBuilder sessionBuilder) {
        this.sessionBuilder = sessionBuilder;
    }

    @Override
    public MiniEmail init(String nickName, Class<? extends BaseMiniEmail> clazz) {
        String fromName = sessionBuilder.getProps("username");

        // 如果有定制发件人定制昵称，此处作处理
        if (!StringUtils.isEmpty(nickName) && !nickName.equals(sessionBuilder.getProps("username"))) {
            try {
                fromName = MimeUtility.encodeText(nickName) + " <" + sessionBuilder.getProps("username") + ">";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        BaseMiniEmail baseMiniEmail = null;
        try {
            baseMiniEmail = clazz.getDeclaredConstructor(MimeMessage.class, String.class)
                    .newInstance(new MimeMessage(sessionBuilder.parseSession()), fromName);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (null == baseMiniEmail) {
            throw new RuntimeException("初始化 MiniEmail 失败，请检查类是否继承 BaseMiniEmail。");
        }

        return baseMiniEmail;
    }

    @Override
    public MiniEmail init(String nickName) {
        return this.init(nickName, DefaultMiniEmail.class);
    }

    @Override
    public MiniEmail init() {
        return this.init(null, DefaultMiniEmail.class);
    }

}
