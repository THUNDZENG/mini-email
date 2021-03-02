package io.github.thundzeng.miniemail.core.defaults;

import io.github.thundzeng.miniemail.builder.EmailSessionBuilder;
import io.github.thundzeng.miniemail.constant.EmailTypeEnum;
import io.github.thundzeng.miniemail.core.MiniEmail;
import io.github.thundzeng.miniemail.core.MiniEmailFactory;
import io.github.thundzeng.miniemail.core.impl.HtmlMiniEmail;
import io.github.thundzeng.miniemail.core.impl.TextMiniEmail;
import io.github.thundzeng.miniemail.util.StringUtils;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

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
    public MiniEmail init(String subject, String fromName, EmailTypeEnum emailTypeEnum) {
        // 如果有定制发件人姓名，此处作处理
        if (!StringUtils.isEmpty(fromName) && !fromName.equals(sessionBuilder.getProps("username"))) {
            try {
                fromName = MimeUtility.encodeText(fromName) + " <" + sessionBuilder.getProps("username") + ">";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        MiniEmail miniEmail;
        switch (emailTypeEnum) {
            case HTML:
                miniEmail = new HtmlMiniEmail(new MimeMessage(sessionBuilder.parseSession()), subject, fromName);
                break;
            default:
                miniEmail = new TextMiniEmail(new MimeMessage(sessionBuilder.parseSession()), subject, fromName);
                break;
        }

        return miniEmail;
    }

    @Override
    public MiniEmail init(EmailTypeEnum emailTypeEnum) {
        return this.init("", sessionBuilder.getProps("username"), emailTypeEnum);
    }
}
